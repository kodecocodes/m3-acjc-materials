/*
 * Copyright (c) 2024 Your Company. All rights reserved.
 */

package com.yourcompany.android.catnapper.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yourcompany.android.catnapper.R
import com.yourcompany.android.catnapper.ui.theme.CatNapperTheme
import java.time.format.DateTimeFormatter

@Composable
fun DetailScreen(
  itemId: Int?,
  detailViewModel: DetailViewModel = viewModel(),
  contentPadding: PaddingValues = PaddingValues()
) {
  val cat by detailViewModel.cat.observeAsState()

  LaunchedEffect(itemId) {
    itemId?.let { detailViewModel.getCat(it) }
  }

  val scrollState = rememberScrollState()

  cat?.let { cat ->
    Column(
      modifier = Modifier
        .padding(contentPadding)
        .verticalScroll(scrollState)
    ) {
      Image(
        painter = painterResource(id = cat.image),
        contentDescription = stringResource(id = R.string.cat_image_description, cat.name),
        modifier = Modifier
          .fillMaxWidth()
          .height(300.dp)
          .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
        contentScale = ContentScale.Crop,
        alignment = Alignment.TopCenter
      )

      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = cat.name,
            style = MaterialTheme.typography.h5
          )
          Spacer(modifier = Modifier.weight(1f))
          IconToggleButton(
            checked = cat.isFavorite,
            onCheckedChange = { detailViewModel.toggleFavorite() }
          ) {
            Icon(
              imageVector = if (cat.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
              contentDescription = stringResource(id = R.string.detail_favorite_label)
            )
          }
        }

        Text(
          text = stringResource(id = R.string.details_age, cat.age),
          style = MaterialTheme.typography.subtitle1,
          modifier = Modifier.padding(top = 4.dp)
        )
        Text(
          text = cat.notes,
          style = MaterialTheme.typography.body1,
          modifier = Modifier.padding(top = 8.dp)
        )

        if (cat.naps.isNotEmpty()) {
          Divider(modifier = Modifier.padding(vertical = 16.dp))

          Spacer(modifier = Modifier.height(16.dp))

          Text(
            text = stringResource(id = R.string.details_naps),
            style = MaterialTheme.typography.h6
          )
          Column {
            val formatter = DateTimeFormatter.ofPattern("h:mm a")
            cat.naps.forEachIndexed { index, nap ->
              Text(
                text = "${nap.start.format(formatter)} - ${nap.end.format(formatter)}",
              )
            }
          }
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
  CatNapperTheme {
    DetailScreen(itemId = 1)
  }
}
