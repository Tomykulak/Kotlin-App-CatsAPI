package xokruhli.finalproject.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore1: DataStore<Preferences> by preferencesDataStore(name = "preferences")


