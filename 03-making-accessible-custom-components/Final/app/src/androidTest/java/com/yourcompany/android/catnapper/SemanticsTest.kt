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
