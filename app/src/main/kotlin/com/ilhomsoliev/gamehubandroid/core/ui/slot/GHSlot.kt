package com.ilhomsoliev.gamehubandroid.core.ui.slot

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerH
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme

@Composable
fun GHSlot(
  modifier: Modifier = Modifier,
  icon: ImageVector,
  text: String,
  subtitle: String? = null,
  actionIcon: ImageVector? = null,
  hasDivider: Boolean = false,
  subcontent: (@Composable () -> Unit)? = null,
  onClick: (() -> Unit)? = null
) {
  Column(
    modifier = modifier
      .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(40.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        modifier = Modifier.weight(1f),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(icon, contentDescription = "", tint = AppTheme.colors.onSurface)
        SpacerH(12.dp)
        Column {
          Text(
            text = text,
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colors.onSurface,
          )
          if (subtitle != null) {
            SpacerV(4.dp)
            Text(
              text = subtitle,
              style = AppTheme.typography.bodyMedium,
              color = AppTheme.colors.onSurfaceVar,
            )
          }
        }
      }

      Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
      ) {
        actionIcon?.let {
          Icon(actionIcon, contentDescription = "", tint = AppTheme.colors.onSurface)
        }
      }
    }

    if (subcontent != null) {
      SpacerV(12.dp)
      subcontent.invoke()
    }

    if (hasDivider) {
      SpacerV(8.dp)
      HorizontalDivider(thickness = 0.5.dp, color = AppTheme.colors.onSurfaceVar)
      SpacerV(8.dp)
    }
  }
}