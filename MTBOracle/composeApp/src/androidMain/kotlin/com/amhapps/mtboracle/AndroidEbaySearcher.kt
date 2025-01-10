package com.amhapps.mtboracle

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AndroidEbaySearcher(val context:Context):EbaySearcher() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tokens")
    override fun getCachedToken(): String {
        val accessTokenKey = stringPreferencesKey("accessToken")
        val exampleCounterFlow: Flow<String> = context.dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[accessTokenKey] ?: ""
            }

    }

    override fun cacheToken(token: String) {
        TODO("Not yet implemented")
    }

}