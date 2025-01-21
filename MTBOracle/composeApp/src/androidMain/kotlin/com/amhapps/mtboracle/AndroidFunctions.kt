package com.amhapps.mtboracle

import BikeData
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

suspend fun androidCacheBike(bikeData: BikeData,context: Context){
    println("Android Caching")
    val keyStrs = listOf("bike0","bike1","bike2","bike3","bike4")
    val keys = keyStrs.map{ str -> stringPreferencesKey(str) }
    val prevBikesFlow: Flow<MutableList<String>> = context.prevBikesDataStore.data
        .map { preferences ->
            val result = mutableListOf<String>()
            for (i in 0..4) {
                if(preferences[keys[i]]!=null){
                    result.add(preferences[keys[i]]!!)
                }
            }
            println("flow result $result")
            result
        }
    println("Waiting for first")
    val prevStoredBikes = prevBikesFlow.first()
    println("Got count")
    val builder = GsonBuilder()
    val gson : Gson =  builder.create()
    val jsonBike = gson.toJson(bikeData)
    println("Num bikes ${prevStoredBikes.size}")
    if(prevStoredBikes.size<5){
        val bikeKey = stringPreferencesKey("bike${prevStoredBikes.size}") // 0 indexed
        context.prevBikesDataStore.edit { prevBikes ->
            prevBikes [bikeKey] = jsonBike
        }
        println("Stored $bikeKey $jsonBike")
    }
    else{
        context.prevBikesDataStore.edit { prevBikes ->
            for(i in 0..3){
                prevBikes[keys[i]] = prevStoredBikes[i+1]
            }
            prevBikes[keys[4]] = jsonBike
        }
    }

}