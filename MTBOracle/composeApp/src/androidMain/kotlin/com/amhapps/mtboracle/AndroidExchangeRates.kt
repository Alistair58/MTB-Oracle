package com.amhapps.mtboracle

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.amhapps.mtboracle.screens.ExchangeRates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AndroidExchangeRates(private val context: Context): ExchangeRates() {
    override suspend fun getCachedRate(currency: String,currDate:String): Float {
        val exchangeRateDateKey = stringPreferencesKey("exchangeRateDate")
        val dateFlow: Flow<String> = context.exchangeRatesDataStore.data //a flow is an asynchronous stream
            .map { preferences ->
                preferences[exchangeRateDateKey] ?: ""
            }
        val date = dateFlow.first()
        println("Date stored: "+date)
        if(date=="" || date!=currDate) return -1f;
        val currencyKey = floatPreferencesKey(currency)
        val rateFlow: Flow<Float> = context.exchangeRatesDataStore.data
            .map { preferences ->
                preferences[currencyKey] ?: -1f;
            }
        println("Rate stored for $currency: "+rateFlow.first())
        return rateFlow.first()
    }

    override suspend fun cacheRate(currency: String, rate: Float) {
        val currencyKey = floatPreferencesKey(currency)
        context.exchangeRatesDataStore.edit { exchangeRates ->
            exchangeRates [currencyKey] = rate
        }
    }
    override suspend fun storeDate(currDate:String){ //One date for all rates
        val exchangeRateDateKey = stringPreferencesKey("exchangeRateDate")
        context.exchangeRatesDataStore.edit { exchangeRates ->
            exchangeRates [exchangeRateDateKey] = currDate
        }
    }

}