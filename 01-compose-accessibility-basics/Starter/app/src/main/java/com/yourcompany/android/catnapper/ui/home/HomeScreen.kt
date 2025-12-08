/*
 * Copyright (c) 2024 Your Company. All rights reserved.
 */

package com.yourcompany.android.catnapper.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.yourcompany.android.catnapper.R
import com.yourcompany.android.catnapper.data.Cat
import com.yourcompany.android.catnapper.ui.navigation.Screen
import com.yourcompany.android.catnapper.ui.theme.CatNapperTheme

private val AvatarSize = 48.dp
private val ItemPadding = 8.dp

@Composable
fun HomeScreen(
  navController: NavController,
  homeViewModel: HomeViewModel = viewModel(),
  contentPadding: PaddingValues = PaddingValues()
) {
  val cats by homeViewModel.cats.observeAsState(initial = emptyList())

  LazyColumn(contentPadding = contentPadding) {
    items(cats) { cat ->
      HomeItem(
        cat = cat,
        onItemClicked = { navController.navigate(Screen.Detail.createRoute(cat.id)) },
        onFavoriteClicked = { homeViewModel.toggleFavoriteStatus(cat.id) }
      )
    }
  }
}

@Composable
fun HomeItem(
  cat: Cat,
  onItemClicked: (Cat) -> Unit,
  onFavoriteClicked: () -> Unit
) {
  val favoriteActionLabel = if (cat.isFavorite) {
    stringResource(id = R.string.action_label_unfavorite)
  } else {
    stringResource(id = R.string.action_label_favorite)
  }

  Card(
    modifier = Modifier
      .padding(ItemPadding)
      .fillMaxWidth()
      .clickable { onItemClicked(cat) },
    elevation = 4.dp
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(ItemPadding)
    ) {
      Image(
        painter = painterResource(id = cat.image),
        contentDescription = null, // decorative
        modifier = Modifier
          .size(AvatarSize)
          .clip(CircleShape)
      )
      Text(
        text = cat.name,
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(start = ItemPadding)
      )
      Spacer(modifier = Modifier.weight(1f))
      if (cat.isFavorite) {
        Icon(
          imageVector = Icons.Filled.Favorite,
          contentDescription = null // decorative
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  CatNapperTheme {
    HomeScreen(navController = rememberNavController())
  }
}
