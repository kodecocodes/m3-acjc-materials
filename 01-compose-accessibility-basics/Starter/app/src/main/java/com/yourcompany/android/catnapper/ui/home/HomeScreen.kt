/*
 * Copyright (c) $today.year Kodeco Inc
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
      CatRow(
        cat = cat,
        onItemClicked = { navController.navigate(Screen.Detail.createRoute(cat.id)) },
        onFavoriteClicked = { homeViewModel.toggleFavoriteStatus(cat.id) }
      )
    }
  }
}

@Composable
fun CatRow(
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
