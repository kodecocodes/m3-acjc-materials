/*
 * Copyright (c) 2024 Your Company. All rights reserved.
 */

package com.yourcompany.android.catnapper.data

import com.yourcompany.android.catnapper.R
import java.time.LocalTime

object CatRepository {
  private var cats = listOf(
    Cat(
      1, "Luna", R.drawable.cat_1, 4, "A very playful cat.", listOf(
        Nap(LocalTime.of(10, 0), LocalTime.of(11, 30)),
        Nap(LocalTime.of(13, 0), LocalTime.of(14, 0)),
        Nap(LocalTime.of(16, 0), LocalTime.of(17, 45)),
        Nap(LocalTime.of(22, 0), LocalTime.of(6, 0))
      )
    ),
    Cat(
      2, "Oliver", R.drawable.cat_2, 2, "Loves to cuddle.", listOf(
        Nap(LocalTime.of(9, 0), LocalTime.of(12, 30)),
        Nap(LocalTime.of(14, 0), LocalTime.of(17, 0))
      )
    ),
    Cat(
      3, "Leo", R.drawable.cat_3, 7, "A bit of a loner.", listOf(
        Nap(LocalTime.of(2, 0), LocalTime.of(8, 0)),
        Nap(LocalTime.of(20, 0), LocalTime.of(23, 0))
      )
    ),
    Cat(
      4, "Milo", R.drawable.cat_4, 1, "Very energetic.", listOf(
        Nap(LocalTime.of(1, 0), LocalTime.of(3, 0)),
        Nap(LocalTime.of(5, 0), LocalTime.of(6, 0)),
        Nap(LocalTime.of(13, 0), LocalTime.of(15, 0)),
        Nap(LocalTime.of(18, 0), LocalTime.of(20, 0))
      )
    ),
    Cat(
      5, "Bella", R.drawable.cat_5, 5, "Loves to eat.", listOf(
        Nap(LocalTime.of(0, 0), LocalTime.of(8, 0)),
        Nap(LocalTime.of(9, 0), LocalTime.of(17, 0))
      ), isFavorite = true
    ),
    Cat(
      6, "Charlie", R.drawable.cat_6, 3, "A very vocal cat.", listOf(
        Nap(LocalTime.of(6, 0), LocalTime.of(7, 30)),
        Nap(LocalTime.of(9, 0), LocalTime.of(10, 0)),
        Nap(LocalTime.of(12, 0), LocalTime.of(13, 0)),
        Nap(LocalTime.of(15, 0), LocalTime.of(16, 45))
      )
    )
  )

  fun getCats(): List<Cat> = cats

  fun getCat(id: Int): Cat? = cats.find { it.id == id }

}
