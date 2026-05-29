package com.ilhomsoliev.gamehubandroid.feature.details.data

import com.ilhomsoliev.gamehubandroid.core.network.safeApiCall
import com.ilhomsoliev.gamehubandroid.core.result.Result
import com.ilhomsoliev.gamehubandroid.feature.game.data.API_KEY
import com.ilhomsoliev.gamehubandroid.feature.game.data.baseUrl
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RedditApi(
  private val client: HttpClient,
) {
  suspend fun getGameRedditPosts(gameId: Int): Result<RedditPostResponse> =
    withContext(Dispatchers.IO) {
      safeApiCall {
        client.get("$baseUrl/games/$gameId/reddit") {
          parameter("key", API_KEY)
          parameter("ordering", "-created")
          parameter("page_size", 20)
        }
      }
    }

}