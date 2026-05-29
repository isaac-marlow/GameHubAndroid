package com.ilhomsoliev.gamehubandroid.feature.search.data

data class GameSearchQuery(
  // 1. Core Text Search
  val searchQuery: String? = null,
  val searchPrecise: Boolean = false, // Disable fuzziness (strict match)
  val searchExact: Boolean = false,   // Mark search as exact match

  // 2. Pagination
  val page: Int = 1,
  val pageSize: Int = 5,

  // 3. Sorting
  val sorting: GameSortOption? = null,

  // 4. Filters (Lists of IDs or Slugs)
  // Example: listOf("pc", "playstation5") or listOf("4", "187")
  val platforms: List<String>? = null,
  val stores: List<String>? = null,
  val developers: List<String>? = null,
  val publishers: List<String>? = null,
  val genres: List<String>? = null,
  val tags: List<String>? = null,
  val creators: List<String>? = null, // Specific people IDs

  // 5. Ranges
  // Format: "2020-01-01,2023-12-31"
  val dates: String? = null,
  // Format: "80,100" (Metacritic score range)
  val metacritic: String? = null,
  // Filter by how many platforms the game is on (e.g., 1 for exclusives)
  val platformsCount: Int? = null,

  // 6. Exclusions (Clean up results)
  val excludeAdditions: Boolean = false,   // Hide DLCs
  val excludeParents: Boolean = false,     // Hide games that have editions
  val excludeGameSeries: Boolean = false,  // Hide games included in a series
  val excludeCollection: Int? = null       // Exclude specific collection ID
)

/**
 * Type-safe sorting options
 * The "-" prefix means Descending (Highest first).
 * No prefix means Ascending (Lowest first).
 */
enum class GameSortOption(val value: String) {
  RELEVANCE("-relevance"), // Default for search
  NAME("name"),           // A-Z
  NAME_DESC("-name"),     // Z-A
  RELEASED("-released"),  // Newest first
  RELEASED_OLD("released"), // Oldest first
  ADDED("-added"),        // Most popular (added to libraries)
  CREATED("-created"),    // Recently added to database
  UPDATED("-updated"),    // Recently updated data
  RATING("-rating"),      // Best User Rating
  METACRITIC("-metacritic") // Best Critic Score
}