package com.ilhomsoliev.gamehubandroid.core.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import android.util.Log
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
  single {
    HttpClient(Android) {
      // Content Negotiation for JSON serialization
      install(ContentNegotiation) {
        json(
          Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
          }
        )
      }

      // Logging
      install(Logging) {
        logger =
          object : Logger {
            override fun log(message: String) {
              message
                .chunked(3500)
                .forEachIndexed { index, chunk ->
                  Log.d("Ktor", "[$index] $chunk")
                }
            }
          }
        level = LogLevel.ALL
      }

      // Default Request configuration
      install(DefaultRequest) {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        // Add any default headers here (e.g., API keys, auth tokens)
      }

      engine {
        connectTimeout = 30_000
        socketTimeout = 30_000
      }
    }
  }
}
