/*
 * Copyright (c) 2025 Kodeco Inc
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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import com.yourcompany.android.catnapper.R
import com.yourcompany.android.catnapper.data.Nap
import com.yourcompany.android.catnapper.ui.theme.CatNapperTheme
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun SleepBarGraph(naps: List<Nap>, modifier: Modifier = Modifier) {
  Layout(
    content = {
      naps.forEach { nap ->
        val formatter = DateTimeFormatter.ofPattern("h:mm a")
        val description = stringResource(
          id = R.string.nap_content_description,
          nap.start.format(formatter),
          nap.end.format(formatter)
        )
        Box(modifier = Modifier
          .background(MaterialTheme.colors.primary)
          .semantics {
            contentDescription = description
          })
      }
    },
    modifier = modifier.background(Color.LightGray)
  ) { measurables, constraints ->
    val totalMinutes = 24 * 60
    val placeables = measurables.mapIndexed { index, measurable ->
      val nap = naps[index]
      val napDuration = if (nap.end.isBefore(nap.start)) {
        ChronoUnit.MINUTES.between(
          nap.start,
          LocalTime.MAX
        ) + ChronoUnit.MINUTES.between(LocalTime.MIN, nap.end)
      } else {
        ChronoUnit.MINUTES.between(nap.start, nap.end)
      }
      val napWidth = (napDuration / totalMinutes.toFloat() * constraints.maxWidth).toInt()
      measurable.measure(Constraints.fixed(width = napWidth, height = constraints.maxHeight))
    }

    layout(constraints.maxWidth, constraints.maxHeight) {
      var xPosition = 0
      placeables.forEachIndexed { index, placeable ->
        val nap = naps[index]
        val startOffset = ChronoUnit.MINUTES.between(LocalTime.MIN, nap.start)
        xPosition = (startOffset / totalMinutes.toFloat() * constraints.maxWidth).toInt()
        placeable.placeRelative(x = xPosition, y = 0)
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun SleepBarGraphPreview() {
  CatNapperTheme {
    val sampleNaps = listOf(
      Nap(start = LocalTime.of(2, 0), end = LocalTime.of(6, 30)),
      Nap(start = LocalTime.of(13, 0), end = LocalTime.of(14, 0)),
      Nap(start = LocalTime.of(18, 0), end = LocalTime.of(22, 15))
    )
    SleepBarGraph(naps = sampleNaps, modifier = Modifier.fillMaxSize())
  }
}
