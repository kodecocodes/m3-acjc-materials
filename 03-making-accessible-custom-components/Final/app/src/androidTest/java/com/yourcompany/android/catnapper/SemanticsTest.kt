/*
 * Copyright (c) 2024 Your Company. All rights reserved.
 */

package com.yourcompany.android.catnapper

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsToggleable
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isHeading
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.yourcompany.android.catnapper.ui.theme.CatNapperTheme
import org.junit.Rule
import org.junit.Test

class SemanticsTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun detailScreen_verifyToggleAndHeading() {
    composeTestRule.setContent {
      CatNapperTheme { CatNapperApp() }
    }

    // 1. Navigate to the detail screen for the first cat
    composeTestRule.onNodeWithText("Luna")
      .performClick()

    // 2. Verify that the "Favorite" icon is toggleable
    composeTestRule
      .onNode(hasContentDescription("Favorite", substring = true, ignoreCase = true))
      .assertIsToggleable()

    // 3. Verify that the "Naps:" heading is displayed
    composeTestRule.onNode(isHeading() and hasText("Naps:"))
      .assertIsDisplayed()

    composeTestRule.onRoot()
      .printToLog("TESTING123")
  }
}
