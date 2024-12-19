package com.amhapps.mtboracle

import BikeData
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.amhapps.mtboracle.screens.ValuationPage

class AndroidValuationPage(navController: NavHostController, private val bikeData: BikeData, private val context: Context
) : ValuationPage(navController, bikeData){
    @Composable
    override fun show(){
        BackHandler {
//            navController.previousBackStackEntry
//                ?.savedStateHandle
//                ?.set("bikeData",bikeData)
            navController.popBackStack()
        }
        super.show()

    }
    override fun valuation():Float {
        val dataset = AndroidDataset(context)
        val neuralNetwork = AndroidNeuralNetwork(context)
        val bike = AndroidBike(bikeData,dataset)
        return neuralNetwork.process(bike.getValues())

    }
}