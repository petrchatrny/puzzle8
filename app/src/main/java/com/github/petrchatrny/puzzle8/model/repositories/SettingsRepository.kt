package com.github.petrchatrny.puzzle8.model.repositories

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import com.github.petrchatrny.puzzle8.model.entities.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val PREFERENCE_NAME = "settings"

class SettingsRepository(context: Context) {

    private val dataStore = context.createDataStore(
        name = PREFERENCE_NAME
    )

    private object PreferenceKeys {
        val layout = preferencesKey<String>("layout")
        val goal = preferencesKey<String>("goal")
    }

    suspend fun saveSettings(settings: Settings) {
        dataStore.edit {
            it[PreferenceKeys.layout] = settings.layout
            it[PreferenceKeys.goal] = settings.goal
        }
    }

    fun getSettings(): Flow<Settings> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {
            val layout = it[PreferenceKeys.layout] ?: "3x3"
            val goal = it[PreferenceKeys.goal] ?: "123456780"
            Settings(layout, goal)
        }
}