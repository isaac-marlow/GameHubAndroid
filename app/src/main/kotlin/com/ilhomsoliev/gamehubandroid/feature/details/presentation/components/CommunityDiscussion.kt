package com.ilhomsoliev.gamehubandroid.feature.details.presentation.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhomsoliev.gamehubandroid.core.ui.GlideAsyncImage
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerH
import com.ilhomsoliev.gamehubandroid.core.ui.SpacerV
import com.ilhomsoliev.gamehubandroid.core.ui.modifier.gameHubRoundedBackground
import com.ilhomsoliev.gamehubandroid.core.ui.theme.AppTheme
import com.ilhomsoliev.gamehubandroid.feature.details.domain.RedditPostModel

@Composable
fun CommunityDiscussion(
  modifier: Modifier = Modifier,
  posts: List<RedditPostModel>,
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
    Row(
      modifier = Modifier
        .fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Text(
        text = "Community Discussions",
        style = AppTheme.typography.title.copy(fontSize = 18.sp),
        color = AppTheme.colors.text
      )
    }

    SpacerV(12.dp)

    if (posts.isEmpty()) {
      Text(
        text = "No community discussions yet.",
        style = AppTheme.typography.body,
        color = AppTheme.colors.secondaryText
      )
    } else {
      posts.take(3).forEachIndexed { index, post ->
        CommunityDiscussionItem(post = post)
        if (index != posts.lastIndex) {
          SpacerV(12.dp)
        }
      }
    }
  }
}

@Composable
private fun CommunityDiscussionItem(
  post: RedditPostModel,
) {
  val context = LocalContext.current
  val cardBackgroundColor = Color(0xFF2C2C2C)
  val titleColor = Color(0xFFEEEEEE)
  val bodyColor = Color(0xFFAAAAAA)
  val footerColor = Color(0xFF888888)
  val contentText = remember(post.contentHtml) {
    post.contentHtml
      ?.let { AnnotatedString.fromHtml(it).text }
      ?.trim()
      ?.takeIf { it.isNotBlank() }
  }
  val preview = remember(contentText) {
    contentText?.let {
      if (it.length > 140) "${it.take(140)}..." else it
    }
  }

  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .wrapContentHeight()
      .clickable {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.externalUrl))
        context.startActivity(intent)
      },
    color = cardBackgroundColor,
    shape = RoundedCornerShape(12.dp)
  ) {
    Column(
      modifier = Modifier.padding(16.dp)
    ) {
      Text(
        text = post.title,
        color = titleColor,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
      )

      SpacerV(8.dp)

      Text(
        text = preview.toString(),
        color = bodyColor,
        fontSize = 15.sp,
        lineHeight = 22.sp
      )

      SpacerV(16.dp)
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        // to use a real image from your resources.
        GlideAsyncImage(
          imageUrl = post.imageUrl,
          contentDescription = "User Avatar",
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(Color.Gray)

        )

        SpacerH(8.dp)

        Text(
          text = "${post.author} • ${post.createdDate}",
          color = footerColor,
          fontSize = 14.sp
        )
      }
    }
  }
}