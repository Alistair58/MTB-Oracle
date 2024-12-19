package com.amhapps.mtboracle

import BikeData
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.amhapps.mtboracle.screens.BrandAndModelForm

class AndroidBrandAndModelForm(private val navController: NavHostController, private var bikeData:BikeData):BrandAndModelForm(navController,bikeData){
    @Composable
    override fun ShowForm(){
        BackHandler {
            //TODO losing form warning
            navController.popBackStack()
        }
        super.ShowForm()
    }

}