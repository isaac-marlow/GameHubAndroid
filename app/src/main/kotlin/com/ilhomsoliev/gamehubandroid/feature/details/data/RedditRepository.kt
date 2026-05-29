package com.ilhomsoliev.gamehubandroid.feature.details.data

import com.ilhomsoliev.gamehubandroid.core.result.Result
import com.ilhomsoliev.gamehubandroid.feature.details.domain.RedditPostModel
import com.ilhomsoliev.gamehubandroid.feature.details.domain.toDomain

class RedditRepository(
  private val redditApi: RedditApi
) {
  suspend fun getPosts(gameId: Int): Result<List<RedditPostModel>> {
    val result: Result<RedditPostResponse> = redditApi.getGameRedditPosts(gameId)
    return if (result.isSuccess) {
      Result.Success((result as Result.Success).data.posts.map { it.toDomain() })
    } else {
      Result.Error((result as Result.Error).exception)
    }
  }
}