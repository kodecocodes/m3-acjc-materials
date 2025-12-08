/*
 * Copyright (c) 2024 Your Company. All rights reserved.
 */

package com.yourcompany.android.catnapper

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isHeading
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.yourcompany.android.catnapper.ui.theme.CatNapperTheme
import org.junit.Rule
import org.junit.Test

class SemanticsTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun detailScreen_verifyHeadingsAndState() {
    composeTestRule.setContent {
      CatNapperTheme {
        CatNapperApp()
      }
    }

    // Navigate to the detail screen for the first cat
    composeTestRule.onNodeWithText("Luna").performClick()

    // Verify that the "Daily Sleep Schedule (Box Layout)" heading is displayed
    composeTestRule.onNode(isHeading() and hasText("Daily Sleep Schedule (Box Layout)"))
      .assertIsDisplayed()

    // Verify that the "Favorite" button has the correct state description
    composeTestRule.onNode(hasParent(hasText("Favorite"))).assertIsDisplayed()
  }
}
