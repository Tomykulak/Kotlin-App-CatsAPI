package xokruhli.finalproject.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore by preferencesDataStore(name = "imageDataStore")

class DataStoreRepositoryImpl(private val context: Context) : IDataStoreRepository {

    override suspend fun saveImageState(url: String, downloaded: Boolean) {
        val key = booleanPreferencesKey(url)
        context.dataStore.edit { preferences ->
            preferences[key] = downloaded
        }
    }

    override suspend fun isImageDownloaded(url: String): Boolean {
        val key = booleanPreferencesKey(url)
        val preferences = context.dataStore.data.first()
        return preferences[key] ?: false
    }
}