package com.amhapps.mtboracle

import BikeData
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.amhapps.mtboracle.screens.ExchangeRates
import com.amhapps.mtboracle.screens.SimilarBikesPage
import com.amhapps.mtboracle.screens.ValuationPage
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AndroidValuationPage(navController: NavHostController, private val bikeData: AndroidBikeData, private val context:Context, private val dataset: AndroidDataset, private val neuralNetwork: AndroidNeuralNetwork,
) : ValuationPage(navController, bikeData){

    @Composable
    override fun show(){
        BackHandler {
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("bikeData",bikeData)
            navController.popBackStack()
        }
        
        
        val similarBikesPage = AndroidSimilarBikesPage(navController,bikeData,context)
        Box( //Needed for the home button
            modifier = Modifier
                .fillMaxSize(),
        ) {
            var ebayBikes by remember { mutableStateOf(listOf<EbayBikeData>()) }
            var status by remember { mutableIntStateOf(0) }
            var sortBy by remember { mutableStateOf("Best Match") }
            var exchangeRate by remember { mutableFloatStateOf(-1f) }
            val currencyCode = countryToCurrency(bikeData.country)
            var similarBikesMedian by remember { mutableFloatStateOf(-1f) }

            similarBikesPage.RetrieveBikesAndExchangeRate(ebayBikes,{ebayBikes = it},status,{status = it},sortBy,currencyCode,{exchangeRate = it},similarBikesMedian,{similarBikesMedian = it})
            LazyColumn(
                userScrollEnabled = true,
                state = rememberLazyListState(),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth() //Funny wiggling if this isn't here
            )
            {
                item{body(exchangeRate, similarBikesMedian)}
                similarBikesPage.SimilarBikesBody(this@LazyColumn,false,
                    ebayBikes,{ebayBikes = it},
                    status,{status = it},
                    sortBy,{sortBy = it})
            }

            Column(
                modifier = Modifier.align(Alignment.BottomCenter)
            ){
                HomeButton()
            }
        }





    }
    override fun valuation():Float {
        val bike = AndroidBike(bikeData,dataset)
        return neuralNetwork.process(bike.getValues())
    }
    @Composable
    override fun HomeButton(){
        Button(
            onClick = {
                navController.popBackStack(AndroidHomeScreen,false)
            },
            colors = MTBOracleTheme.buttonColors,
            modifier = Modifier
                .padding(0.dp, 10.dp)
                .width(200.dp)
                .height(60.dp)
        ){
            Text(
                "Home",
                fontSize = 30.sp,
                color = Color.White
            )
        }
    }

    override suspend fun cacheBike(bikeData: BikeData) {
        androidCacheBike(bikeData,context)
    }


}