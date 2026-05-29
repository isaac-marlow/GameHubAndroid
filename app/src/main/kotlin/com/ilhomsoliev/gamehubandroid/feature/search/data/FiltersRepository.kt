package com.ilhomsoliev.gamehubandroid.feature.search.data

import android.database.Cursor
import com.ilhomsoliev.gamehubandroid.core.result.Result
import com.ilhomsoliev.gamehubandroid.core.local.database.GameHubDatabaseHelper
import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.StoreDto
import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.GenreDto
import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.TagDto
import com.ilhomsoliev.gamehubandroid.feature.game.data.dto.platform.PlatformDto
import com.ilhomsoliev.gamehubandroid.feature.game.domain.DeveloperModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.GenreModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.PlatformModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.PublisherModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.StoreModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.TagModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.toDomain
import com.ilhomsoliev.gamehubandroid.feature.search.data.network.FilterApi
import com.ilhomsoliev.gamehubandroid.feature.search.data.network.dto.DeveloperDto
import com.ilhomsoliev.gamehubandroid.feature.search.data.network.dto.PublisherDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import androidx.core.database.sqlite.transaction

class FiltersRepository(
  private val filterApi: FilterApi,
  private val dbHelper: GameHubDatabaseHelper,
) {

  fun getPopularGenres(): Flow<Result<List<GenreModel>>> = flow {
    val cachedGenres = getCachedGenres()
    if (cachedGenres.isNotEmpty()) {
      emit(Result.Success(cachedGenres))
    } else {
      emit(Result.Loading)
    }

    when (val result = filterApi.getPopularGenres()) {
      is Result.Success -> {
        saveGenres(result.data.genres)
        emit(Result.Success(result.data.genres.map { it.toDomain() }))
      }
      is Result.Error -> {
        if (cachedGenres.isEmpty()) {
          emit(Result.Error(result.exception))
        }
      }
      Result.Loading -> emit(Result.Loading)
    }
  }

  fun getPopularTags(): Flow<Result<List<TagModel>>> = flow {
    val cachedTags = getCachedTags()
    if (cachedTags.isNotEmpty()) {
      emit(Result.Success(cachedTags))
    } else {
      emit(Result.Loading)
    }

    when (val result = filterApi.getPopularTags()) {
      is Result.Success -> {
        saveTags(result.data.tags)
        emit(Result.Success(result.data.tags.map { it.toDomain() }))
      }

      is Result.Error -> {
        if (cachedTags.isEmpty()) {
          emit(Result.Error(result.exception))
        }
      }

      Result.Loading -> emit(Result.Loading)
    }
  }

  fun getPlatforms(): Flow<Result<List<PlatformModel>>> = flow {
    val cachedPlatforms = getCachedPlatforms()
    if (cachedPlatforms.isNotEmpty()) {
      emit(Result.Success(cachedPlatforms))
    } else {
      emit(Result.Loading)
    }

    when (val result = filterApi.getPlatforms()) {
      is Result.Success -> {
        savePlatforms(result.data.platforms)
        emit(Result.Success(result.data.platforms.map { it.toDomain() }))
      }
      is Result.Error -> {
        if (cachedPlatforms.isEmpty()) {
          emit(Result.Error(result.exception))
        }
      }
      Result.Loading -> emit(Result.Loading)
    }
  }

  fun getStores(): Flow<Result<List<StoreModel>>> = flow {
    val cachedStores = getCachedStores()
    if (cachedStores.isNotEmpty()) {
      emit(Result.Success(cachedStores))
    } else {
      emit(Result.Loading)
    }

    when (val result = filterApi.getStores()) {
      is Result.Success -> {
        saveStores(result.data.stores)
        emit(Result.Success(result.data.stores.map { it.toDomain() }))
      }
      is Result.Error -> {
        if (cachedStores.isEmpty()) {
          emit(Result.Error(result.exception))
        }
      }
      Result.Loading -> emit(Result.Loading)
    }
  }

  fun getDevelopers(): Flow<Result<List<DeveloperModel>>> = flow {
    val cachedDevelopers = getCachedDevelopers()
    if (cachedDevelopers.isNotEmpty()) {
      emit(Result.Success(cachedDevelopers))
    } else {
      emit(Result.Loading)
    }

    when (val result = filterApi.getDevelopers()) {
      is Result.Success -> {
        saveDevelopers(result.data.developers)
        emit(Result.Success(result.data.developers.map { it.toDomain() }))
      }
      is Result.Error -> {
        if (cachedDevelopers.isEmpty()) {
          emit(Result.Error(result.exception))
        }
      }
      Result.Loading -> emit(Result.Loading)
    }
  }

  fun getPublishers(): Flow<Result<List<PublisherModel>>> = flow {
    val cachedPublishers = getCachedPublishers()
    if (cachedPublishers.isNotEmpty()) {
      emit(Result.Success(cachedPublishers))
    } else {
      emit(Result.Loading)
    }

    when (val result = filterApi.getPublishers()) {
      is Result.Success -> {
        savePublishers(result.data.publishers)
        emit(Result.Success(result.data.publishers.map { it.toDomain() }))
      }
      is Result.Error -> {
        if (cachedPublishers.isEmpty()) {
          emit(Result.Error(result.exception))
        }
      }
      Result.Loading -> emit(Result.Loading)
    }
  }

  private fun saveTags(tags: List<TagDto>) {
    val db = dbHelper.writableDatabase
    db.transaction {
      try {
        execSQL("DELETE FROM tags")
        tags.forEach { tag ->
          execSQL(
            "INSERT OR REPLACE INTO tags(id, name, slug, language, games_count, image_background) VALUES(?, ?, ?, ?, ?, ?)",
            arrayOf(tag.id, tag.name, tag.slug, tag.language, tag.gamesCount, tag.imageBackground)
          )
        }
      } finally {
      }
    }
  }

  private fun saveGenres(genres: List<GenreDto>) {
    val db = dbHelper.writableDatabase
    db.transaction {
      try {
        execSQL("DELETE FROM genres")
        genres.forEach { genre ->
          execSQL(
            "INSERT OR REPLACE INTO genres(id, name, slug, image_background, games_count) VALUES(?, ?, ?, ?, ?)",
            arrayOf(genre.id, genre.name, genre.slug, genre.imageBackground, genre.gamesCount)
          )
        }
      } finally {
      }
    }
  }

  private fun saveDevelopers(developers: List<DeveloperDto>) {
    val db = dbHelper.writableDatabase
    db.transaction {
      try {
        execSQL("DELETE FROM developers")
        developers.forEach { developer ->
          execSQL(
            "INSERT OR REPLACE INTO developers(id, name, slug, games_count, image_background) VALUES(?, ?, ?, ?, ?)",
            arrayOf(
              developer.id,
              developer.name,
              developer.slug,
              developer.gamesCount,
              developer.imageBackground
            )
          )
        }
      } finally {
      }
    }
  }

  private fun savePlatforms(platforms: List<PlatformDto>) {
    val db = dbHelper.writableDatabase
    db.beginTransaction()
    try {
      db.execSQL("DELETE FROM platforms")
      platforms.forEach { platform ->
        db.execSQL(
          "INSERT OR REPLACE INTO platforms(id, name, slug) VALUES(?, ?, ?)",
          arrayOf(platform.id, platform.name, platform.slug)
        )
      }
      db.setTransactionSuccessful()
    } finally {
      db.endTransaction()
    }
  }

  private fun saveStores(stores: List<StoreDto>) {
    val db = dbHelper.writableDatabase
    db.beginTransaction()
    try {
      db.execSQL("DELETE FROM stores")
      stores.forEach { store ->
        db.execSQL(
          "INSERT OR REPLACE INTO stores(id, name, slug) VALUES(?, ?, ?)",
          arrayOf(store.id, store.name, store.slug)
        )
      }
      db.setTransactionSuccessful()
    } finally {
      db.endTransaction()
    }
  }

  private fun savePublishers(publishers: List<PublisherDto>) {
    val db = dbHelper.writableDatabase
    db.beginTransaction()
    try {
      db.execSQL("DELETE FROM publishers")
      publishers.forEach { publisher ->
        db.execSQL(
          "INSERT OR REPLACE INTO publishers(id, name, slug, games_count, image_background) VALUES(?, ?, ?, ?, ?)",
          arrayOf(
            publisher.id,
            publisher.name,
            publisher.slug,
            publisher.gamesCount,
            publisher.imageBackground
          )
        )
      }
      db.setTransactionSuccessful()
    } finally {
      db.endTransaction()
    }
  }

  private fun getCachedTags(): List<TagModel> {
    val tags = mutableListOf<TagModel>()
    dbHelper.readableDatabase.rawQuery(
      "SELECT id, name, slug, language, games_count, image_background FROM tags",
      null
    ).use { cursor ->
      while (cursor.moveToNext()) {
        tags.add(
          TagModel(
            id = cursor.getIntByName("id"),
            name = cursor.getStringByName("name"),
            slug = cursor.getStringByName("slug"),
            language = cursor.getStringOrNullByName("language"),
            gamesCount = cursor.getIntByName("games_count"),
            imageBackground = cursor.getStringOrNullByName("image_background")
          )
        )
      }
    }
    return tags
  }

  private fun getCachedGenres(): List<GenreModel> {
    val genres = mutableListOf<GenreModel>()
    dbHelper.readableDatabase.rawQuery(
      "SELECT id, name, slug, image_background, games_count FROM genres",
      null
    ).use { cursor ->
      while (cursor.moveToNext()) {
        genres.add(
          GenreModel(
            id = cursor.getIntByName("id"),
            name = cursor.getStringByName("name"),
            slug = cursor.getStringByName("slug"),
            imageBackground = cursor.getStringOrNullByName("image_background"),
            gamesCount = cursor.getIntByName("games_count")
          )
        )
      }
    }
    return genres
  }

  private fun getCachedPlatforms(): List<PlatformModel> {
    val platforms = mutableListOf<PlatformModel>()
    dbHelper.readableDatabase.rawQuery(
      "SELECT id, name, slug FROM platforms",
      null
    ).use { cursor ->
      while (cursor.moveToNext()) {
        platforms.add(
          PlatformModel(
            id = cursor.getIntByName("id"),
            name = cursor.getStringByName("name"),
            slug = cursor.getStringByName("slug"),
            releasedAt = null,
            requirements = null
          )
        )
      }
    }
    return platforms
  }

  private fun getCachedStores(): List<StoreModel> {
    val stores = mutableListOf<StoreModel>()
    dbHelper.readableDatabase.rawQuery(
      "SELECT id, name, slug FROM stores",
      null
    ).use { cursor ->
      while (cursor.moveToNext()) {
        stores.add(
          StoreModel(
            id = cursor.getIntByName("id"),
            name = cursor.getStringByName("name"),
            slug = cursor.getStringByName("slug")
          )
        )
      }
    }
    return stores
  }

  private fun getCachedDevelopers(): List<DeveloperModel> {
    val developers = mutableListOf<DeveloperModel>()
    dbHelper.readableDatabase.rawQuery(
      "SELECT id, name, slug, games_count, image_background FROM developers",
      null
    ).use { cursor ->
      while (cursor.moveToNext()) {
        developers.add(
          DeveloperModel(
            id = cursor.getIntByName("id"),
            name = cursor.getStringByName("name"),
            slug = cursor.getStringByName("slug"),
            gamesCount = cursor.getIntByName("games_count"),
            imageBackground = cursor.getStringOrNullByName("image_background")
          )
        )
      }
    }
    return developers
  }

  private fun getCachedPublishers(): List<PublisherModel> {
    val publishers = mutableListOf<PublisherModel>()
    dbHelper.readableDatabase.rawQuery(
      "SELECT id, name, slug, games_count, image_background FROM publishers",
      null
    ).use { cursor ->
      while (cursor.moveToNext()) {
        publishers.add(
          PublisherModel(
            id = cursor.getIntByName("id"),
            name = cursor.getStringByName("name"),
            slug = cursor.getStringByName("slug"),
            gamesCount = cursor.getIntByName("games_count"),
            imageBackground = cursor.getStringOrNullByName("image_background")
          )
        )
      }
    }
    return publishers
  }
}

private fun TagDto.toDomain(): TagModel {
  return TagModel(
    id = id,
    name = name,
    slug = slug,
    language = language,
    gamesCount = gamesCount,
    imageBackground = imageBackground
  )
}

private fun PlatformDto.toDomain(): PlatformModel {
  return PlatformModel(
    id = id,
    name = name,
    slug = slug,
    releasedAt = null,
    requirements = null
  )
}

private fun StoreDto.toDomain(): StoreModel {
  return StoreModel(
    id = id,
    name = name,
    slug = slug
  )
}

private fun DeveloperDto.toDomain(): DeveloperModel {
  return DeveloperModel(
    id = id,
    name = name,
    slug = slug,
    gamesCount = gamesCount,
    imageBackground = imageBackground
  )
}

private fun PublisherDto.toDomain(): PublisherModel {
  return PublisherModel(
    id = id,
    name = name,
    slug = slug,
    gamesCount = gamesCount,
    imageBackground = imageBackground
  )
}

private fun Cursor.getIntByName(column: String): Int = getInt(getColumnIndexOrThrow(column))
private fun Cursor.getStringByName(column: String): String = getString(getColumnIndexOrThrow(column))
private fun Cursor.getStringOrNullByName(column: String): String? {
  val index = getColumnIndexOrThrow(column)
  return if (isNull(index)) null else getString(index)
}