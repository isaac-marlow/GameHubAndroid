package com.ilhomsoliev.gamehubandroid.core.network

import com.ilhomsoliev.gamehubandroid.core.result.Result
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Helper function to safely execute Ktor API calls and map exceptions to Result.Error.
 * This provides a consistent error handling pattern across all network calls.
 */
suspend inline fun <reified T> safeApiCall(
  crossinline apiCall: suspend () -> HttpResponse
): Result<T> {
  return withContext(Dispatchers.IO) {
    try {
      val response = apiCall()
      if (response.status.value in 200..299) {
        Result.Success(response.body<T>())
      } else {
        Result.Error(
          kotlin.Exception("HTTP ${response.status.value}: ${response.status.description}")
        )
      }
    } catch (e: UnresolvedAddressException) {
      Result.Error(kotlin.Exception("Network error: Unable to reach server", e))
    } catch (e: Exception) {
      Result.Error(e)
    }
  }
}
