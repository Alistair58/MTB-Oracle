package com.amhapps.mtboracle

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.amhapps.mtboracle.screens.SizeMaterialTravelForm

class WebSizeMaterialTravelForm(
private val navController: NavHostController,
private var bikeDataInput:WebBikeData,
private val isValuation:Boolean
) : SizeMaterialTravelForm(navController,bikeDataInput,isValuation) {

    @Composable
    override fun NextButton(
        size: String,
        wSize: String,
        fTravel: String,
        rTravel: String,
        material: String
    ) {
        TODO("Not yet implemented")
    }
}