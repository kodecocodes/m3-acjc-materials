/*
 * Copyright (c) 2024 Your Company. All rights reserved.
 */

package com.yourcompany.android.catnapper.data

import androidx.annotation.DrawableRes

data class Cat(
  val id: Int,
  val name: String,
  @DrawableRes val image: Int,
  val age: Int,
  val notes: String,
  val naps: List<Nap> = emptyList(),
  val isFavorite: Boolean = false
)
