package com.amhapps.mtboracle

import BikeData
import Dataset
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

class AndroidValuationPage(navController: NavHostController,val bikeData: BikeData,val context:Context
) :ValuationPage(navController, bikeData) {

    override fun valuation():Float {
        val dataset = AndroidDataset(context)
        val neuralNetwork = AndroidNeuralNetwork(context)
        val bike = AndroidBike(bikeData,dataset)
        return neuralNetwork.process(bike.getValues())

    }
}