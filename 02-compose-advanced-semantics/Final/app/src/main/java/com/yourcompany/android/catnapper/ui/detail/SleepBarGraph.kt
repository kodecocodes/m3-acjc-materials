/*
 * Copyright (c) 2024 Your Company. All rights reserved.
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import com.yourcompany.android.catnapper.data.Nap
import com.yourcompany.android.catnapper.ui.theme.CatNapperTheme
import java.time.LocalTime
import java.time.temporal.ChronoUnit

@Composable
fun SleepBarGraph(naps: List<Nap>, modifier: Modifier = Modifier) {
  Layout(
    content = {
      naps.forEach { _ ->
        Box(
          modifier = Modifier
            .background(MaterialTheme.colors.primary)
        )
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
