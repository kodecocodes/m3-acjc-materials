/*
 * Copyright (c) 2024 Your Company. All rights reserved.
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
