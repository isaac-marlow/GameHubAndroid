package com.ilhomsoliev.gamehubandroid.feature.search.data.network

import com.ilhomsoliev.gamehubandroid.core.network.safeApiCall
import com.ilhomsoliev.gamehubandroid.core.result.Result
import com.ilhomsoliev.gamehubandroid.feature.game.data.API_KEY
import com.ilhomsoliev.gamehubandroid.feature.game.data.baseUrl
import com.ilhomsoliev.gamehubandroid.feature.search.data.network.dto.DeveloperListResponse
import com.ilhomsoliev.gamehubandroid.feature.search.data.network.dto.GenreResponse
import com.ilhomsoliev.gamehubandroid.feature.search.data.network.dto.PlatformListResponse
import com.ilhomsoliev.gamehubandroid.feature.search.data.network.dto.PublisherListResponse
import com.ilhomsoliev.gamehubandroid.feature.search.data.network.dto.StoreListResponse
import com.ilhomsoliev.gamehubandroid.feature.search.data.network.dto.TagResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilterApi(
  private val client: HttpClient,
) {
  suspend fun getPopularGenres(): Result<GenreResponse> = withContext(Dispatchers.IO) {
    safeApiCall {
      client.get("$baseUrl/genres") {
        parameter("key", API_KEY)
        parameter("ordering", "-games_count")
        parameter("page_size", 20)
      }
    }
  }

  suspend fun getPopularTags(): Result<TagResponse> = withContext(Dispatchers.IO) {
    safeApiCall {
      client.get("$baseUrl/tags") {
        parameter("key", API_KEY)
        parameter("ordering", "-games_count")
        parameter("page_size", 20)
      }
    }
  }

  suspend fun getPlatforms(): Result<PlatformListResponse> = withContext(Dispatchers.IO) {
    safeApiCall {
      client.get("$baseUrl/platforms") {
        parameter("key", API_KEY)
        parameter("ordering", "-games_count")
        parameter("page_size", 40)
      }
    }
  }

  suspend fun getStores(): Result<StoreListResponse> = withContext(Dispatchers.IO) {
    safeApiCall {
      client.get("$baseUrl/stores") {
        parameter("key", API_KEY)
        parameter("ordering", "-games_count")
        parameter("page_size", 20)
      }
    }
  }

  suspend fun getDevelopers(): Result<DeveloperListResponse> = withContext(Dispatchers.IO) {
    safeApiCall {
      client.get("$baseUrl/developers") {
        parameter("key", API_KEY)
        parameter("ordering", "-games_count")
        parameter("page_size", 20)
      }
    }
  }

  suspend fun getPublishers(): Result<PublisherListResponse> = withContext(Dispatchers.IO) {
    safeApiCall {
      client.get("$baseUrl/publishers") {
        parameter("key", API_KEY)
        parameter("ordering", "-games_count")
        parameter("page_size", 20)
      }
    }
  }
}