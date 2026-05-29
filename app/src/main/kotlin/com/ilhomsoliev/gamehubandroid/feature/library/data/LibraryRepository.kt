package com.ilhomsoliev.gamehubandroid.feature.library.data

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.ilhomsoliev.gamehubandroid.core.local.database.GameHubDatabaseHelper
import com.ilhomsoliev.gamehubandroid.feature.game.domain.GameModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.GenreModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.PlatformModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.StoreModel
import com.ilhomsoliev.gamehubandroid.feature.game.domain.TagModel

// TODO advanced local search
class LibraryRepository(
  private val dbHelper: GameHubDatabaseHelper,
) {

  fun getLibraryGames(): List<GameModel> {
    val db = dbHelper.readableDatabase
    val games = mutableListOf<GameModel>()
    db.rawQuery(
      """
      SELECT id, slug, name, release_date, release_year, is_tba, background_image, rating
      FROM games
      ORDER BY added_at DESC
      """.trimIndent(),
      null
    ).use { cursor ->
      while (cursor.moveToNext()) {
        val gameId = cursor.getIntByName("id")
        games.add(
          GameModel(
            id = gameId,
            slug = cursor.getStringByName("slug"),
            name = cursor.getStringByName("name"),
            releasedDate = cursor.getStringOrNullByName("release_date"),
            releaseYear = cursor.getStringOrNullByName("release_year"),
            isTba = cursor.getIntByName("is_tba") == 1,
            backgroundImage = cursor.getStringOrNullByName("background_image"),
            rating = cursor.getStringByName("rating"),
            platforms = getPlatformsByGameId(db, gameId),
            stores = getStoresByGameId(db, gameId),
            genres = getGenresByGameId(db, gameId),
            tags = getTagsByGameId(db, gameId),
            esrbRating = null,
            description = null,
            redditDescription = null
          )
        )
      }
    }
    return games
  }

  fun getSavedGameIds(): Set<Int> {
    val db = dbHelper.readableDatabase
    val ids = mutableSetOf<Int>()
    db.rawQuery("SELECT id FROM games", null).use { cursor ->
      while (cursor.moveToNext()) {
        ids.add(cursor.getIntByName("id"))
      }
    }
    return ids
  }

  fun saveGame(game: GameModel) {
    val db = dbHelper.writableDatabase
    db.beginTransaction()
    try {
      db.execSQL(
        """
        INSERT OR REPLACE INTO games(
          id, slug, name, release_date, release_year, is_tba, background_image, rating, added_at
        ) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)
        """.trimIndent(),
        arrayOf(
          game.id,
          game.slug,
          game.name,
          game.releasedDate,
          game.releaseYear,
          if (game.isTba) 1 else 0,
          game.backgroundImage,
          game.rating,
          System.currentTimeMillis()
        )
      )

      db.execSQL("DELETE FROM games_genres WHERE game_id = ?", arrayOf(game.id))
      game.genres.forEach { genre ->
        db.execSQL(
          "INSERT OR REPLACE INTO genres(id, name, slug, image_background, games_count) VALUES(?, ?, ?, ?, ?)",
          arrayOf(genre.id, genre.name, genre.slug, genre.imageBackground, 0)
        )
        db.execSQL(
          "INSERT OR IGNORE INTO games_genres(game_id, genre_id) VALUES(?, ?)",
          arrayOf(game.id, genre.id)
        )
      }

      db.execSQL("DELETE FROM games_platforms WHERE game_id = ?", arrayOf(game.id))
      game.platforms.forEach { platform ->
        db.execSQL(
          "INSERT OR REPLACE INTO platforms(id, name, slug) VALUES(?, ?, ?)",
          arrayOf(platform.id, platform.name, platform.slug)
        )
        db.execSQL(
          "INSERT OR IGNORE INTO games_platforms(game_id, platform_id) VALUES(?, ?)",
          arrayOf(game.id, platform.id)
        )
      }

      db.execSQL("DELETE FROM games_stores WHERE game_id = ?", arrayOf(game.id))
      game.stores.forEach { store ->
        db.execSQL(
          "INSERT OR REPLACE INTO stores(id, name, slug) VALUES(?, ?, ?)",
          arrayOf(store.id, store.name, store.slug)
        )
        db.execSQL(
          "INSERT OR IGNORE INTO games_stores(game_id, store_id) VALUES(?, ?)",
          arrayOf(game.id, store.id)
        )
      }

      db.execSQL("DELETE FROM games_tags WHERE game_id = ?", arrayOf(game.id))
      game.tags.forEach { tag ->
        db.execSQL(
          "INSERT OR REPLACE INTO tags(id, name, slug, language, games_count, image_background) VALUES(?, ?, ?, ?, ?, ?)",
          arrayOf(tag.id, tag.name, tag.slug, tag.language, 0, tag.imageBackground)
        )
        db.execSQL(
          "INSERT OR IGNORE INTO games_tags(game_id, tag_id) VALUES(?, ?)",
          arrayOf(game.id, tag.id)
        )
      }
      db.setTransactionSuccessful()
    } finally {
      db.endTransaction()
    }
  }

  fun deleteGame(gameId: Int) {
    val db = dbHelper.writableDatabase
    db.beginTransaction()
    try {
      db.execSQL("DELETE FROM games_genres WHERE game_id = ?", arrayOf(gameId))
      db.execSQL("DELETE FROM games_platforms WHERE game_id = ?", arrayOf(gameId))
      db.execSQL("DELETE FROM games_stores WHERE game_id = ?", arrayOf(gameId))
      db.execSQL("DELETE FROM games_tags WHERE game_id = ?", arrayOf(gameId))
      db.execSQL("DELETE FROM games WHERE id = ?", arrayOf(gameId))
      db.setTransactionSuccessful()
    } finally {
      db.endTransaction()
    }
  }

  private fun getGenresByGameId(db: SQLiteDatabase, gameId: Int): List<GenreModel> {
    val genres = mutableListOf<GenreModel>()
    db.rawQuery(
      """
      SELECT g.id, g.name, g.slug, g.image_background, g.games_count
      FROM genres g
      INNER JOIN games_genres gg ON gg.genre_id = g.id
      WHERE gg.game_id = ?
      """.trimIndent(),
      arrayOf(gameId.toString())
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

  private fun getPlatformsByGameId(
    db: SQLiteDatabase,
    gameId: Int
  ): List<PlatformModel> {
    val platforms = mutableListOf<PlatformModel>()
    db.rawQuery(
      """
      SELECT p.id, p.name, p.slug
      FROM platforms p
      INNER JOIN games_platforms gp ON gp.platform_id = p.id
      WHERE gp.game_id = ?
      """.trimIndent(),
      arrayOf(gameId.toString())
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

  private fun getStoresByGameId(db: SQLiteDatabase, gameId: Int): List<StoreModel> {
    val stores = mutableListOf<StoreModel>()
    db.rawQuery(
      """
      SELECT s.id, s.name, s.slug
      FROM stores s
      INNER JOIN games_stores gs ON gs.store_id = s.id
      WHERE gs.game_id = ?
      """.trimIndent(),
      arrayOf(gameId.toString())
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

  private fun getTagsByGameId(db: SQLiteDatabase, gameId: Int): List<TagModel> {
    val tags = mutableListOf<TagModel>()
    db.rawQuery(
      """
      SELECT t.id, t.name, t.slug, t.language, t.image_background
      FROM tags t
      INNER JOIN games_tags gt ON gt.tag_id = t.id
      WHERE gt.game_id = ?
      """.trimIndent(),
      arrayOf(gameId.toString())
    ).use { cursor ->
      while (cursor.moveToNext()) {
        tags.add(
          TagModel(
            id = cursor.getIntByName("id"),
            name = cursor.getStringByName("name"),
            slug = cursor.getStringByName("slug"),
            language = cursor.getStringOrNullByName("language"),
            gamesCount = 0,
            imageBackground = cursor.getStringOrNullByName("image_background")
          )
        )
      }
    }
    return tags
  }
}

private fun Cursor.getIntByName(column: String): Int = getInt(getColumnIndexOrThrow(column))
private fun Cursor.getStringByName(column: String): String = getString(getColumnIndexOrThrow(column))
private fun Cursor.getStringOrNullByName(column: String): String? {
  val index = getColumnIndexOrThrow(column)
  return if (isNull(index)) null else getString(index)
}
