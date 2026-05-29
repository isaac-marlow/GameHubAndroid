package com.ilhomsoliev.gamehubandroid.core.result

/**
 * Generic sealed class for handling operation results.
 * Used throughout the app to represent Success, Error, and Loading states.
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()

    val isSuccess: Boolean
        get() = this is Success

    val isError: Boolean
        get() = this is Error

    val isLoading: Boolean
        get() = this is Loading

    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }

    fun getOrThrow(): T = when (this) {
        is Success -> data
        is Error -> throw exception
        is Loading -> throw kotlin.IllegalStateException("Result is still loading")
    }
}