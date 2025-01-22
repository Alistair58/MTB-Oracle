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
            result
        }
    val prevStoredBikeStrs = prevBikesFlow.first() //List of existing stored bikes
    val gson : Gson =  GsonBuilder().create()
    val prevStoredBikeObjs = (prevStoredBikeStrs //Turn all bikes in objects and remove duplicates
        .map{bikeStr -> gson.fromJson(bikeStr,AndroidBikeData::class.java)}
        .filter {storedBike-> !storedBike.isSameBike(bikeData)} + bikeData)
    val updatedBikes = prevStoredBikeObjs //Remove oldest item if we have 5 and turn them into json strs
        .subList(if(prevStoredBikeObjs.size>5) 1 else 0,prevStoredBikeObjs.size)
        .map{bikeObj -> gson.toJson(bikeObj)}


    context.prevBikesDataStore.edit { prevBikes ->
        for(i in updatedBikes.indices){
            prevBikes[keys[i]] = updatedBikes[i]
        }
    }

}
//println("Android Caching")
//println("flow result $result")
//println("Waiting for first")
//println("Got count")
//println("Num bikes ${updatedBikes.size}")