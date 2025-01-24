package com.amhapps.mtboracle

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.amhapps.mtboracle.screens.CategoryConditionCountryForm

class WebCategoryConditionCountryForm(
    private val navController: NavHostController,
    private var bikeDataInput:WebBikeData,
    private val isValuation:Boolean
) : CategoryConditionCountryForm(navController,bikeDataInput,isValuation) {

    @Composable
    override fun NextButton(category: String, condition: String, country: String) {
        TODO("Not yet implemented")
    }
}