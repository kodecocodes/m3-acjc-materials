/*
 * Copyright (c) 2024 Your Company. All rights reserved.
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
