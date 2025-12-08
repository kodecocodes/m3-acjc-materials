/*
 * Copyright (c) 2024 Your Company. All rights reserved.
 */

package com.yourcompany.android.catnapper.ui.home

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

class HomeViewModel(application: Application) : AndroidViewModel(application) {

  private val userPreferencesRepository =
    getApplication<CatNapperApplication>().userPreferencesRepository

  private val _cats = MutableLiveData<List<Cat>>()
  val cats: LiveData<List<Cat>> = _cats

  init {
    viewModelScope.launch {
      userPreferencesRepository.favoriteCats.collect { favoriteIds ->
        val catsFromRepo = CatRepository.getCats()
        _cats.value = catsFromRepo.map { cat ->
          cat.copy(isFavorite = favoriteIds.contains(cat.id.toString()))
        }
      }
    }
  }

  fun toggleFavoriteStatus(catId: Int) {
    viewModelScope.launch {
      val currentFavorites = userPreferencesRepository.favoriteCats.first()
      val newFavorites = if (currentFavorites.contains(catId.toString())) {
        currentFavorites - catId.toString()
      } else {
        currentFavorites + catId.toString()
      }
      userPreferencesRepository.updateFavoriteCats(newFavorites)
    }
  }
}
