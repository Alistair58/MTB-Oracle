package com.amhapps.mtboracle

import BikeData
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.amhapps.mtboracle.screens.SizeMaterialTravelForm

class AndroidSizeMaterialTravelForm(private val navController: NavHostController, private var bikeData:BikeData) : SizeMaterialTravelForm(navController,bikeData) {
    @Composable
    override fun ShowForm(){
        BackHandler {
//            navController.previousBackStackEntry
//                ?.savedStateHandle
//                ?.set("bikeData",bikeData)
            navController.popBackStack()
        }
        super.ShowForm()
    }

}