/*
 * Copyright (c) $today.year Kodeco Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


package com.yourcompany.android.catnapper.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yourcompany.android.catnapper.CatNapperApplication
import com.yourcompany.android.catnapper.data.Cat
import com.yourcompany.android.catnapper.data.CatRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {

  private val userPreferencesRepository =
    getApplication<CatNapperApplication>().userPreferencesRepository

  private val _cat = MutableLiveData<Cat>()
  val cat: LiveData<Cat> = _cat

  fun getCat(id: Int) {
    viewModelScope.launch {
      userPreferencesRepository.favoriteCats.collect { favoriteCats ->
        val catRaw = CatRepository.getCat(id)
        val cat = catRaw?.copy(isFavorite = favoriteCats.contains(id.toString()))
        _cat.value = cat ?: catRaw
      }
    }
  }

  fun toggleFavorite() {
    viewModelScope.launch {
      val catId = _cat.value?.id.toString()
      val currentFavorites = userPreferencesRepository.favoriteCats.first()
      val newFavorites = if (currentFavorites.contains(catId)) {
        currentFavorites - catId
      } else {
        currentFavorites + catId
      }
      userPreferencesRepository.updateFavoriteCats(newFavorites)
    }
  }
}
