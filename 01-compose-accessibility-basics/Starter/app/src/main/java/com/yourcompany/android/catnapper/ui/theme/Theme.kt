/*
 * Copyright (c) 2024 Your Company. All rights reserved.
 */

package com.yourcompany.android.catnapper.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
  primary = colorPrimaryDark,
  primaryVariant = colorPrimary,
  secondary = colorAccent
)

private val LightColorPalette = lightColors(
  primary = colorPrimary,
  primaryVariant = colorPrimaryDark,
  secondary = colorAccent
)

@Composable
fun CatNapperTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colors = if (darkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }

  MaterialTheme(
    colors = colors,
    typography = Type,
    shapes = Shapes,
    content = content
  )
}
