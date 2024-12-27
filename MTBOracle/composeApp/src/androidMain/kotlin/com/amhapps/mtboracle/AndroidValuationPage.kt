package com.amhapps.mtboracle

import BikeData
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.amhapps.mtboracle.screens.ValuationPage

class AndroidValuationPage(navController: NavHostController, private val bikeData: AndroidBikeData, private val dataset: AndroidDataset,private val neuralNetwork: AndroidNeuralNetwork
) : ValuationPage(navController, bikeData){
    @Composable
    override fun show(){
        BackHandler {
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("bikeData",bikeData)
            navController.popBackStack()
        }
        super.show()

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