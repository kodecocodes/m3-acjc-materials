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

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.yourcompany.android.catnapper.data.Nap
import com.yourcompany.android.catnapper.ui.theme.CatNapperTheme
import java.time.LocalTime
import java.time.temporal.ChronoUnit

@Composable
fun SleepChart(naps: List<Nap>, modifier: Modifier = Modifier) {
  val primaryColor = MaterialTheme.colors.primary

  Canvas(
    modifier = modifier.clipToBounds()
  ) {
    // Draw the light gray background for the chart
    drawRect(color = Color.LightGray)

    val totalMinutesInDay = 24 * 60f

    naps.forEach { nap ->
      // 1. Calculate duration in minutes, handling overnight naps correctly.
      val napDurationInMinutes = if (nap.end.isBefore(nap.start)) {
        ChronoUnit.MINUTES.between(
          nap.start,
          LocalTime.MAX
        ) + ChronoUnit.MINUTES.between(LocalTime.MIN, nap.end)
      } else {
        ChronoUnit.MINUTES.between(nap.start, nap.end)
      }

      // 2. Calculate the starting position as an offset from the beginning of the day.
      val startOffsetInMinutes = ChronoUnit.MINUTES.between(LocalTime.MIN, nap.start)

      // 3. Convert minutes to pixel values for width and position.
      val napWidth = size.width * (napDurationInMinutes / totalMinutesInDay)
      val xPosition = size.width * (startOffsetInMinutes / totalMinutesInDay)

      // 4. Draw the rectangle for the nap.
      drawRect(
        color = primaryColor,
        topLeft = Offset(x = xPosition, y = 0f),
        size = Size(width = napWidth, height = size.height)
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun SleepChartPreview() {
  CatNapperTheme {
    val sampleNaps = listOf(
      Nap(start = LocalTime.of(2, 0), end = LocalTime.of(6, 30)),
      Nap(start = LocalTime.of(13, 0), end = LocalTime.of(14, 0)),
      // This nap goes overnight to better visualize the logic
      Nap(start = LocalTime.of(22, 0), end = LocalTime.of(4, 0))
    )
    SleepChart(naps = sampleNaps, modifier = Modifier.fillMaxSize())
  }
}
