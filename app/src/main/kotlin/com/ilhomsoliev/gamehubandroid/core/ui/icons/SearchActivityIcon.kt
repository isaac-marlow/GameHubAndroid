package com.ilhomsoliev.gamehubandroid.core.ui.icons


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
val SearchActivityIcon: ImageVector
  get() {
    if (_search_activity != null) {
      return _search_activity!!
    }
    _search_activity =
      ImageVector.Builder(
        name = "search_activity",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
      )
        .apply {
          path(
            fill = SolidColor(Color.Black),
            fillAlpha = 1f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.Companion.NonZero,
          ) {
            moveTo(14.36f, 4.64f)
            quadTo(14f, 4.27f, 14f, 3.75f)
            quadTo(14f, 3.22f, 14.36f, 2.86f)
            reflectiveQuadTo(15.25f, 2.5f)
            quadToRelative(0.53f, 0f, 0.89f, 0.36f)
            reflectiveQuadTo(16.5f, 3.75f)
            quadToRelative(0f, 0.52f, -0.36f, 0.89f)
            quadTo(15.78f, 5f, 15.25f, 5f)
            reflectiveQuadTo(14.36f, 4.64f)
            close()
            moveToRelative(0f, 16.5f)
            quadTo(14f, 20.78f, 14f, 20.25f)
            reflectiveQuadToRelative(0.36f, -0.89f)
            reflectiveQuadTo(15.25f, 19f)
            quadToRelative(0.53f, 0f, 0.89f, 0.36f)
            reflectiveQuadToRelative(0.36f, 0.89f)
            reflectiveQuadToRelative(-0.36f, 0.89f)
            quadTo(15.78f, 21.5f, 15.25f, 21.5f)
            reflectiveQuadTo(14.36f, 21.14f)
            close()
            moveToRelative(4f, -13f)
            quadTo(18f, 7.77f, 18f, 7.25f)
            quadTo(18f, 6.72f, 18.36f, 6.36f)
            reflectiveQuadTo(19.25f, 6f)
            quadToRelative(0.53f, 0f, 0.89f, 0.36f)
            quadTo(20.5f, 6.72f, 20.5f, 7.25f)
            quadToRelative(0f, 0.52f, -0.36f, 0.89f)
            reflectiveQuadTo(19.25f, 8.5f)
            quadToRelative(-0.52f, 0f, -0.89f, -0.36f)
            close()
            moveToRelative(0f, 9.5f)
            quadTo(18f, 17.27f, 18f, 16.75f)
            reflectiveQuadToRelative(0.36f, -0.89f)
            reflectiveQuadTo(19.25f, 15.5f)
            quadToRelative(0.53f, 0f, 0.89f, 0.36f)
            quadToRelative(0.36f, 0.36f, 0.36f, 0.89f)
            reflectiveQuadToRelative(-0.36f, 0.89f)
            reflectiveQuadTo(19.25f, 18f)
            quadToRelative(-0.52f, 0f, -0.89f, -0.36f)
            close()
            moveToRelative(1.5f, -4.75f)
            quadTo(19.5f, 12.52f, 19.5f, 12f)
            reflectiveQuadToRelative(0.36f, -0.89f)
            reflectiveQuadToRelative(0.89f, -0.36f)
            reflectiveQuadToRelative(0.89f, 0.36f)
            quadTo(22f, 11.48f, 22f, 12f)
            quadToRelative(0f, 0.52f, -0.36f, 0.89f)
            reflectiveQuadToRelative(-0.89f, 0.36f)
            quadToRelative(-0.52f, 0f, -0.89f, -0.36f)
            close()
            moveTo(12f, 22f)
            quadTo(9.93f, 22f, 8.1f, 21.21f)
            quadTo(6.28f, 20.43f, 4.93f, 19.08f)
            quadTo(3.58f, 17.73f, 2.79f, 15.9f)
            reflectiveQuadTo(2f, 12f)
            quadTo(2f, 9.92f, 2.79f, 8.1f)
            quadTo(3.58f, 6.27f, 4.93f, 4.93f)
            quadTo(6.28f, 3.57f, 8.1f, 2.79f)
            quadTo(9.93f, 2f, 12f, 2f)
            verticalLineTo(4f)
            quadTo(8.65f, 4f, 6.33f, 6.32f)
            reflectiveQuadTo(4f, 12f)
            reflectiveQuadToRelative(2.33f, 5.68f)
            reflectiveQuadTo(12f, 20f)
            verticalLineToRelative(2f)
            close()
            moveToRelative(3.3f, -5.3f)
            lineTo(11f, 12.4f)
            verticalLineTo(7f)
            horizontalLineToRelative(2f)
            verticalLineToRelative(4.6f)
            lineToRelative(3.7f, 3.7f)
            lineToRelative(-1.4f, 1.4f)
            close()
          }
        }
        .build()
    return _search_activity!!
  }

private var _search_activity: ImageVector? = null