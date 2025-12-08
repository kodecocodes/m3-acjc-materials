/*
 * Copyright (c) 2024 Your Company. All rights reserved.
 */

package com.yourcompany.android.catnapper.ui.navigation

sealed class Screen(val route: String) {
  object Home : Screen("home")
  object Detail : Screen("detail/{itemId}") {
    fun createRoute(itemId: Int) = "detail/$itemId"
  }
}
