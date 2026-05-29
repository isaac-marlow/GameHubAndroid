package com.ilhomsoliev.gamehubandroid.feature.details.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
import com.ilhomsoliev.gamehubandroid.core.ui.StoreComponent
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.gameHubRoundedBackground
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import com.ilhomsoliev.gamehubandroid.feature.game.domain.StoreModel

@Composable
fun LazyItemScope.WhereToBuy(
  modifier: Modifier = Modifier,
  stores: List<StoreModel>
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 12.dp)
      .gameHubRoundedBackground()
      .padding(16.dp),
    horizontalAlignment = Alignment.Start,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      text = "Where to buy",
      style = AppTheme.typography.title.copy(fontSize = 18.sp),
      color = AppTheme.colors.text
    )

    SpacerV(16.dp)

    LazyRow(modifier = Modifier.fillMaxWidth()) {
      items(items = stores, key = { it.id }) { store ->
        StoreComponent(storeModel = store)
      }
    }
  }
}