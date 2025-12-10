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
