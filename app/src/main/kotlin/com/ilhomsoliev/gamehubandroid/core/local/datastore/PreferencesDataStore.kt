package com.ilhomsoliev.gamehubandroid.core.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Interface for type-safe key-value data storage using DataStore.
 * 
 * This provides a clean API for storing and retrieving preferences.
 * 
 * Usage example:
 * ```
 * // In your ViewModel or Repository
 * class MyViewModel(
 *     private val preferencesDataStore: PreferencesDataStore
 * ) {
 *     suspend fun saveUserName(name: String) {
 *         preferencesDataStore.putString(Keys.USER_NAME, name)
 *     }
 *     
 *     fun getUserName(): Flow<String?> {
 *         return preferencesDataStore.getString(Keys.USER_NAME)
 *     }
 * }
 * ```
 */
interface PreferencesDataStore {
    /**
     * Store a String value
     */
    suspend fun putString(key: Preferences.Key<String>, value: String)
    
    /**
     * Retrieve a String value as Flow
     */
    fun getString(key: Preferences.Key<String>): Flow<String?>
    
    /**
     * Retrieve a String value once (suspending)
     */
    suspend fun getStringOnce(key: Preferences.Key<String>): String?
    
    /**
     * Store an Int value
     */
    suspend fun putInt(key: Preferences.Key<Int>, value: Int)
    
    /**
     * Retrieve an Int value as Flow
     */
    fun getInt(key: Preferences.Key<Int>): Flow<Int?>
    
    /**
     * Retrieve an Int value once (suspending)
     */
    suspend fun getIntOnce(key: Preferences.Key<Int>): Int?
    
    /**
     * Store a Long value
     */
    suspend fun putLong(key: Preferences.Key<Long>, value: Long)
    
    /**
     * Retrieve a Long value as Flow
     */
    fun getLong(key: Preferences.Key<Long>): Flow<Long?>
    
    /**
     * Retrieve a Long value once (suspending)
     */
    suspend fun getLongOnce(key: Preferences.Key<Long>): Long?
    
    /**
     * Store a Float value
     */
    suspend fun putFloat(key: Preferences.Key<Float>, value: Float)
    
    /**
     * Retrieve a Float value as Flow
     */
    fun getFloat(key: Preferences.Key<Float>): Flow<Float?>
    
    /**
     * Retrieve a Float value once (suspending)
     */
    suspend fun getFloatOnce(key: Preferences.Key<Float>): Float?
    
    /**
     * Store a Boolean value
     */
    suspend fun putBoolean(key: Preferences.Key<Boolean>, value: Boolean)
    
    /**
     * Retrieve a Boolean value as Flow
     */
    fun getBoolean(key: Preferences.Key<Boolean>): Flow<Boolean?>
    
    /**
     * Retrieve a Boolean value once (suspending)
     */
    suspend fun getBooleanOnce(key: Preferences.Key<Boolean>): Boolean?
    
    /**
     * Remove a preference by key
     */
    suspend fun remove(key: Preferences.Key<*>)
    
    /**
     * Clear all preferences
     */
    suspend fun clear()
}

/**
 * Implementation of PreferencesDataStore using DataStore<Preferences>
 */
class PreferencesDataStoreImpl(
    private val dataStore: DataStore<Preferences>
) : PreferencesDataStore {
    
    override suspend fun putString(key: Preferences.Key<String>, value: String) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
    
    override fun getString(key: Preferences.Key<String>): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[key]
        }
    }
    
    override suspend fun getStringOnce(key: Preferences.Key<String>): String? {
        return dataStore.data.first()[key]
    }
    
    override suspend fun putInt(key: Preferences.Key<Int>, value: Int) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
    
    override fun getInt(key: Preferences.Key<Int>): Flow<Int?> {
        return dataStore.data.map { preferences ->
            preferences[key]
        }
    }
    
    override suspend fun getIntOnce(key: Preferences.Key<Int>): Int? {
        return dataStore.data.first()[key]
    }
    
    override suspend fun putLong(key: Preferences.Key<Long>, value: Long) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
    
    override fun getLong(key: Preferences.Key<Long>): Flow<Long?> {
        return dataStore.data.map { preferences ->
            preferences[key]
        }
    }
    
    override suspend fun getLongOnce(key: Preferences.Key<Long>): Long? {
        return dataStore.data.first()[key]
    }
    
    override suspend fun putFloat(key: Preferences.Key<Float>, value: Float) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
    
    override fun getFloat(key: Preferences.Key<Float>): Flow<Float?> {
        return dataStore.data.map { preferences ->
            preferences[key]
        }
    }
    
    override suspend fun getFloatOnce(key: Preferences.Key<Float>): Float? {
        return dataStore.data.first()[key]
    }
    
    override suspend fun putBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
    
    override fun getBoolean(key: Preferences.Key<Boolean>): Flow<Boolean?> {
        return dataStore.data.map { preferences ->
            preferences[key]
        }
    }
    
    override suspend fun getBooleanOnce(key: Preferences.Key<Boolean>): Boolean? {
        return dataStore.data.first()[key]
    }
    
    override suspend fun remove(key: Preferences.Key<*>) {
        dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }
    
    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
