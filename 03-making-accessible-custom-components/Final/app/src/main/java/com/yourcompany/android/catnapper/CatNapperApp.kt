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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yourcompany.android.catnapper.ui.detail.DetailScreen
import com.yourcompany.android.catnapper.ui.home.HomeScreen
import com.yourcompany.android.catnapper.ui.navigation.Screen
import com.yourcompany.android.catnapper.ui.theme.CatNapperTheme

@Composable
fun CatNapperApp() {
  CatNapperTheme {
    val navController = rememberNavController()

    Scaffold(
      topBar = {
        TopAppBar(
          title = { Text(stringResource(id = R.string.app_name)) },
        )
      }
    ) { innerPadding ->
      Box(modifier = Modifier.padding(top = 48.dp)) {
        NavHost(
          navController = navController,
          startDestination = Screen.Home.route,
          modifier = Modifier.padding(innerPadding),
        ) {
          composable(Screen.Home.route) {
            HomeScreen(navController, contentPadding = innerPadding)
          }
          composable(
            Screen.Detail.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
          ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId")
            DetailScreen(itemId, contentPadding = innerPadding)
          }
        }
      }
    }
  }
}
