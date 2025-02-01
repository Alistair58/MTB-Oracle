package com.amhapps.mtboracle

import BikeData
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import com.amhapps.mtboracle.screens.Homepage
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AndroidHomepage(private val navController: NavController,private val context: Context):Homepage(navController) {
    @Composable
    override fun ValuationButton() {
        Button(
            onClick = {
                navController.navigate(
                    AndroidBrandModelYearScreen(bikeData = AndroidBikeData(),true)
                )
            },
            colors = MTBOracleTheme.buttonColors,
            ){
            Text(text = "Value a bike",
                color = Color.White,
                fontSize = 30.sp)
        }
    }

    @Composable
    override fun SimilarBikesButton() {
        Button(
            onClick = {
                navController.navigate(
                    AndroidBrandModelYearScreen(bikeData = AndroidBikeData(),false)
                )
            },
            colors = MTBOracleTheme.buttonColors,
        ){
            Text(text = "Find Similar Bikes",
                color = Color.White,
                fontSize = 30.sp)
        }
    }

    @Composable
    override fun RecentBikes() {
        var prevBikes by remember { mutableStateOf(listOf<AndroidBikeData>()) }
        //Cannot be a mutable list as this does not cause a recomposition
        val scope = rememberCoroutineScope()
        LaunchedEffect(true) {
            scope.launch {
                val builder = GsonBuilder()
                val gson : Gson =  builder.create()
                val keyStrs = listOf("bike0","bike1","bike2","bike3","bike4")
                val keys = keyStrs.map{ str -> stringPreferencesKey(str) }.reversed() //we want bike 4 first
                val prevBikesFlow: Flow<MutableList<String>> = context.prevBikesDataStore.data
                    .map { preferences ->
                        val result = mutableListOf<String>()
                        for(key in keys) {
                            println(key)
                            if(preferences[key]!=null){
                                println("here $key ${preferences[key]}")
                                result.add(preferences[key]!!)
                            }
                        }
                        println("Returning $result")
                        result
                    }
                val prevStoredBikes = prevBikesFlow.first()
                println("prevStoredBikes $prevStoredBikes length ${prevStoredBikes.size}")
                prevBikes = prevStoredBikes
                    .map{storedBike -> gson.fromJson(storedBike,AndroidBikeData::class.java) }
            }
        }

        Column{
            println("Column prev bikes length ${prevBikes.size}")
            if(prevBikes.isNotEmpty()) Text("Recent Bikes", fontSize = 25.sp)
            for(bike in prevBikes){
                RecentBike(bike,onBikeDelete = {bikeData ->
                    println("Before prev bikes length ${prevBikes.size} $prevBikes")
                    prevBikes = prevBikes.filter { item -> !item.isSameBike(bikeData) }
                    println("After prev bikes length ${prevBikes.size} $prevBikes") })
            }
        }
    }

    override suspend fun removeRecentBike(bikeData: BikeData,onBikeDelete:(BikeData)->Unit){
        val builder = GsonBuilder()
        val gson: Gson = builder.create()
        val keyStrs = listOf("bike0", "bike1", "bike2", "bike3", "bike4")
        val keys = keyStrs.map { str -> stringPreferencesKey(str) }
            .reversed() //we want bike 4 first
        println("Before flow")
        val prevBikesFlow: Flow<Preferences.Key<String>> = context.prevBikesDataStore.data
            .map { preferences ->
                var theKey:Preferences.Key<String> = stringPreferencesKey("")
                for (key in keys) {
                    println("Key $key")
                    if (preferences[key] != null) {

                        val recentBike =
                            gson.fromJson(preferences[key], AndroidBikeData::class.java)
                        println("Key exists $key ${recentBike.brand} ${recentBike.model}")
                        if (recentBike.isSameBike(bikeData)) {
                            println("Found key $key")
                            theKey = key
                            break
                        }
                    }
                }
                println("In flow $theKey")
                theKey
            }
        val keyToRemove = prevBikesFlow.first()
        println("After flow")
        context.prevBikesDataStore.edit{ storedBikes ->
            println("Removing")
            storedBikes.remove(keyToRemove)
            println("Removed $keyToRemove")
            onBikeDelete(bikeData)
        }
    }

    override fun platformValuationScreen(bikeData: BikeData):AndroidValuationScreen {
        return AndroidValuationScreen(AndroidBikeData.fromBikeData(bikeData))
    }

    override fun platformSimilarBikesScreen(bikeData: BikeData):AndroidSimilarBikesScreen {
        return AndroidSimilarBikesScreen(AndroidBikeData.fromBikeData(bikeData))
    }


}
//