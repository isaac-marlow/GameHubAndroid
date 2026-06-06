package com.ilhomsoliev.gamehubandroid.feature.settings

data class SettingsUiState(
  val theme: Theme = Theme.SYSTEM,
  val language: Language = Language.ENGLISH,
  val displayLayout: DisplayLayout = DisplayLayout.Grid,
  // val gameCardSize
  val defaultSorting: LibrarySorting = LibrarySorting.DateAdded,
  val defaultFilter: LibraryFilter = LibraryFilter.AllGames,

  ) {
}

enum class LibrarySorting {
  DateAdded,

}


enum class LibraryFilter {
  AllGames,
}

enum class Theme {
  SYSTEM,
  LIGHT,
  DARK,
}

enum class Language {
  ENGLISH,
  RUSSIAN,
}

enum class DisplayLayout {
  Grid,
  List,
}