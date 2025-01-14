package com.amhapps.mtboracle

import BikeData
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.amhapps.mtboracle.screens.SimilarBikesPage
import com.amhapps.mtboracle.screens.ValuationPage

class AndroidValuationPage(navController: NavHostController, private val bikeData: AndroidBikeData, private val context:Context,private val dataset: AndroidDataset,private val neuralNetwork: AndroidNeuralNetwork
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
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ){
                body()
                //similarBikesPage.SubShow()
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
                .padding(0.dp, 30.dp)
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
}