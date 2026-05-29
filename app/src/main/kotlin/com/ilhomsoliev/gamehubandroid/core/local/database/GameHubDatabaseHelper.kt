package com.ilhomsoliev.gamehubandroid.core.local.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// TODO add indexes
// Предметное область
// Йель работы
// Анализ
// Проектирование
// Опредение архтектеры
// Модель базы данных
// Архитек OML
// натация C4
// моджель системы
// тех стек


class GameHubDatabaseHelper(context: Context) : SQLiteOpenHelper(
  context, "game_hub", null, 2
) {

  init {
    setWriteAheadLoggingEnabled(true)
  }

  override fun onConfigure(db: SQLiteDatabase) {
    super.onConfigure(db)
    db.setForeignKeyConstraintsEnabled(true)
  }

  override fun onCreate(db: SQLiteDatabase) {
    createEsrbRatingTable(db)
    createPlatformsTable(db)
    createStoresTable(db)
    createGenresTable(db)
    createTagsTable(db)
    createScreenshotsTable(db)
    createDevelopersTable(db)
    createPublishersTable(db)
    createGamesTable(db)
    createGamesPlatformsTable(db)
    createGamesStoresTable(db)
    createGamesGenresTable(db)
    createGamesTagsTable(db)

    createAuditTriggers(db)
    createCounterTriggers(db)
  }

  override fun onUpgrade(
    db: SQLiteDatabase,
    oldVersion: Int,
    newVersion: Int
  ) {
    db.execSQL("DROP TABLE IF EXISTS games_tags")
    db.execSQL("DROP TABLE IF EXISTS games_genres")
    db.execSQL("DROP TABLE IF EXISTS games_stores")
    db.execSQL("DROP TABLE IF EXISTS games_platforms")
    db.execSQL("DROP TABLE IF EXISTS games")
    db.execSQL("DROP TABLE IF EXISTS publishers")
    db.execSQL("DROP TABLE IF EXISTS developers")
    db.execSQL("DROP TABLE IF EXISTS screenshots")
    db.execSQL("DROP TABLE IF EXISTS tags")
    db.execSQL("DROP TABLE IF EXISTS genres")
    db.execSQL("DROP TABLE IF EXISTS stores")
    db.execSQL("DROP TABLE IF EXISTS platforms")
    db.execSQL("DROP TABLE IF EXISTS esrb_rating")
    onCreate(db)
  }

  private fun createEsrbRatingTable(db: SQLiteDatabase) {
    db.execSQL(
      """
        CREATE TABLE esrb_rating(
            id INTEGER PRIMARY KEY,
            name TEXT NOT NULL,
            slug TEXT NOT NULL
        )
      """.trimIndent()
    )
  }

  private fun createPlatformsTable(db: SQLiteDatabase) {
    db.execSQL(
      """
        CREATE TABLE platforms(
            id INTEGER PRIMARY KEY,
            name TEXT NOT NULL,
            slug TEXT NOT NULL
        )
        """.trimIndent()
    )
  }

  private fun createStoresTable(db: SQLiteDatabase) {
    db.execSQL(
      """
        CREATE TABLE stores(
            id INTEGER PRIMARY KEY,
            name TEXT NOT NULL,
            slug TEXT NOT NULL
        )
      """.trimIndent()
    )
  }

  private fun createGenresTable(db: SQLiteDatabase) {
    db.execSQL(
      """
        CREATE TABLE genres(
            id INTEGER PRIMARY KEY,
            name TEXT NOT NULL,
            slug TEXT NOT NULL,
            image_background TEXT,
            games_count INTEGER NOT NULL
        )
      """.trimIndent()
    )
  }

  private fun createTagsTable(db: SQLiteDatabase) {
    db.execSQL(
      """
        CREATE TABLE tags(
            id INTEGER PRIMARY KEY,
            name TEXT NOT NULL,
            slug TEXT NOT NULL,
            image_background TEXT,
            games_count INTEGER NOT NULL,
            language TEXT
        )
      """.trimIndent()
    )
  }

  private fun createScreenshotsTable(db: SQLiteDatabase) {
    db.execSQL(
      """
        CREATE TABLE screenshots(
            id INTEGER PRIMARY KEY,
            image TEXT NOT NULL,
            width INTEGER NOT NULL,
            height INTEGER NOT NULL,
            is_deleted BOOLEAN NOT NULL
        )
      """.trimIndent()
    )
  }

  private fun createDevelopersTable(db: SQLiteDatabase) {
    db.execSQL(
      """
        CREATE TABLE developers(
            id INTEGER PRIMARY KEY,
            name TEXT NOT NULL,
            slug TEXT NOT NULL,
            games_count INTEGER NOT NULL,
            image_background TEXT
        )
      """.trimIndent()
    )
  }

  private fun createPublishersTable(db: SQLiteDatabase) {
    db.execSQL(
      """
        CREATE TABLE publishers(
            id INTEGER PRIMARY KEY,
            name TEXT NOT NULL,
            slug TEXT NOT NULL,
            games_count INTEGER NOT NULL,
            image_background TEXT
        )
      """.trimIndent()
    )
  }

  // TODO Add timestamp of adding the game into libarary
  private fun createGamesTable(db: SQLiteDatabase) {
    db.execSQL(
      """
          CREATE TABLE games(
              id INTEGER PRIMARY KEY,
              name TEXT NOT NULL,
              slug TEXT NOT NULL,
              release_date TEXT,
              release_year TEXT,
              is_tba BOOLEAN NOT NULL,
              background_image TEXT,
              rating TEXT NOT NULL,
              esrb_rating_id INTEGER,
              added_at INTEGER NOT NULL,
              FOREIGN KEY (esrb_rating_id) REFERENCES esrb_rating(id)             
          )  
        """.trimIndent()
    )
  }

  private fun createGamesPlatformsTable(db: SQLiteDatabase) {
    db.execSQL(
      """
        CREATE TABLE games_platforms (
            game_id INTEGER NOT NULL,
            platform_id INTEGER NOT NULL,
            PRIMARY KEY (game_id, platform_id),
            FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE,
            FOREIGN KEY (platform_id) REFERENCES platforms(id) ON DELETE CASCADE
        );
      """.trimIndent()
    )
  }

  private fun createGamesStoresTable(db: SQLiteDatabase) {
    db.execSQL(
      """
        CREATE TABLE games_stores (
            game_id INTEGER NOT NULL,
            store_id INTEGER NOT NULL,
            PRIMARY KEY (game_id, store_id),
            FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE,
            FOREIGN KEY (store_id) REFERENCES stores(id) ON DELETE CASCADE
        );
      """.trimIndent()
    )
  }

  private fun createGamesGenresTable(db: SQLiteDatabase) {
    db.execSQL(
      """
        CREATE TABLE games_genres(
            game_id INTEGER NOT NULL,
            genre_id INTEGER NOT NULL,
            PRIMARY KEY (game_id, genre_id),
            FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE,
            FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
        );
      """.trimIndent()
    )
  }

  private fun createGamesTagsTable(db: SQLiteDatabase) {
    db.execSQL(
      """
        CREATE TABLE games_tags(
            game_id INTEGER NOT NULL,
            tag_id INTEGER NOT NULL,
            PRIMARY KEY (game_id, tag_id),
            FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE,
            FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
        );
      """.trimIndent()
    )
  }

  // TODO use in settings to view logs
  // TODO add timestamp to track time
  /*
  SELECT *
FROM game_audit_log
ORDER BY changed_at DESC;
   */
  private fun createAuditTriggers(db: SQLiteDatabase) {
    db.execSQL(
      """
      CREATE TABLE game_audit_log(
          id INTEGER PRIMARY KEY AUTOINCREMENT,
          game_id INTEGER,
          action_made TEXT NOT NULL,
          old_name TEXT,
          new_name TEXT,
          old_rating TEXT,
          new_rating TEXT,
          changed_at INTEGER NOT NULL DEFAULT (unixepoch())
      )
    """.trimIndent()
    )

    db.execSQL(
      """
      CREATE TRIGGER games_update_audit
      AFTER UPDATE OF name, rating ON games
      BEGIN
        INSERT INTO game_audit_log(
          game_id,
          action_made,
          old_name,
          new_name,
          old_rating,
          new_rating
        )
        VALUES(
          old.id,
          'UPDATE',
          old.name,
          new.name,
          old.rating,
          new.rating
        );
      END
    """.trimIndent()
    )

    db.execSQL(
      """
      CREATE TRIGGER games_delete_audit
      AFTER DELETE ON games
      BEGIN
        INSERT INTO game_audit_log(
          game_id,
          action_made,
          old_name,
          old_rating
        )
        VALUES(
          old.id,
          'DELETE',
          old.name,
          old.rating
        );
      END
    """.trimIndent()
    )
  }

  private fun createCounterTriggers(db: SQLiteDatabase) {
    db.execSQL(
      """
      CREATE TRIGGER games_genres_insert_counter
      AFTER INSERT ON games_genres
      BEGIN
        UPDATE genres
        SET local_games_count = local_games_count + 1
        WHERE id = new.genre_id;
      END
    """.trimIndent()
    )

    db.execSQL(
      """
      CREATE TRIGGER games_genres_delete_counter
      AFTER DELETE ON games_genres
      BEGIN
        UPDATE genres
        SET local_games_count = local_games_count - 1
        WHERE id = old.genre_id;
      END
    """.trimIndent()
    )

    db.execSQL(
      """
      CREATE TRIGGER games_tags_insert_counter
      AFTER INSERT ON games_tags
      BEGIN
        UPDATE tags
        SET local_games_count = local_games_count + 1
        WHERE id = new.tag_id;
      END
    """.trimIndent()
    )

    db.execSQL(
      """
      CREATE TRIGGER games_tags_delete_counter
      AFTER DELETE ON games_tags
      BEGIN
        UPDATE tags
        SET local_games_count = local_games_count - 1
        WHERE id = old.tag_id;
      END
    """.trimIndent()
    )
  }
}
