package com.amhapps.mtboracle

import BikeData
import Dataset
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

abstract class ValuationPage(protected val navController: NavHostController, private val bikeData: BikeData) {
    @Composable
    fun show(){
        Column{
            val output = "Your bike is worth " + valuation()
            Text(output)
        }
    }
    abstract fun valuation():Float
}