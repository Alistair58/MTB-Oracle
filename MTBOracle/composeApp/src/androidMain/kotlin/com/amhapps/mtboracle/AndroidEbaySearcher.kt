package com.amhapps.mtboracle

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.Instant
import kotlin.math.exp

class AndroidEbaySearcher(private val context:Context):EbaySearcher() {
    override suspend fun getCachedToken(): String {
        //Check the expiry first as this saves looking up both if it has expired
        val tokenExpiryKey = longPreferencesKey("tokenExpiry")
        val expiryFlow: Flow<Long> = context.tokensDataStore.data //a flow is an asynchronous stream
            .map { preferences ->
                preferences[tokenExpiryKey] ?: -1
            }
        val expiryDate = expiryFlow.first()
        val currTime = System.currentTimeMillis()
        if(expiryDate < 0L || currTime > expiryDate) return ""
        val tokenKey = stringPreferencesKey("token")
        val tokenFlow: Flow<String> = context.tokensDataStore.data
            .map { preferences ->
                preferences[tokenKey] ?: ""
            }
        return tokenFlow.first()

    }

    override suspend fun cacheToken(token: String,lifespanSeconds:Long) {
        val tokenKey = stringPreferencesKey("token")
        context.tokensDataStore.edit { tokens ->
            tokens[tokenKey] = token
        }
        val currTime = System.currentTimeMillis()
        val tokenExpiryKey = longPreferencesKey("tokenExpiry")
        context.tokensDataStore.edit { tokens ->
            tokens[tokenExpiryKey] = currTime + (lifespanSeconds*1000) //seconds to milliseconds
        }
    }

}