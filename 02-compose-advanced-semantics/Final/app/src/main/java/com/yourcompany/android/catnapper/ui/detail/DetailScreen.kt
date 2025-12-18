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
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
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
import androidx.compose.ui.semantics.CollectionInfo
import androidx.compose.ui.semantics.CollectionItemInfo
import androidx.compose.ui.semantics.collectionInfo
import androidx.compose.ui.semantics.collectionItemInfo
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
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
            style = MaterialTheme.typography.h5,
            modifier = Modifier.semantics { heading() },
          )
          Spacer(modifier = Modifier.weight(1f))

          Icon(
            imageVector = if (cat.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = stringResource(
              id =
                if (cat.isFavorite) R.string.detail_favorite_label
                else R.string.detail_not_favorite_label
            ),
            modifier = Modifier.toggleable(
              value = cat.isFavorite,
              onValueChange = { detailViewModel.toggleFavorite() }
            )
          )
        }
        Column(Modifier.semantics(mergeDescendants = true) {}) {
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
        }

        if (cat.naps.isNotEmpty()) {
          Divider(modifier = Modifier.padding(vertical = 16.dp))

          Spacer(modifier = Modifier.height(16.dp))

          Text(
            text = stringResource(id = R.string.details_naps),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.semantics { heading() }
          )
          Column(modifier = Modifier.semantics {
            collectionInfo = CollectionInfo(cat.naps.size, 1)
          }) {
            val formatter = DateTimeFormatter.ofPattern("h:mm a")
            cat.naps.forEachIndexed { index, nap ->
              Text(
                text = "${nap.start.format(formatter)} - ${nap.end.format(formatter)}",
                modifier = Modifier.semantics {
                  collectionItemInfo = CollectionItemInfo(
                    rowIndex = index,
                    rowSpan = 1,
                    columnIndex = 0,
                    columnSpan = 1
                  )
                }
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
