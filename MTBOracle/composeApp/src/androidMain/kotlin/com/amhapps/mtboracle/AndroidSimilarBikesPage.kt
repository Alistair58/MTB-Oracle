package com.amhapps.mtboracle

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.amhapps.mtboracle.screens.SimilarBikesPage

class AndroidSimilarBikesPage(private val navController: NavController,private val bikeDataInput:AndroidBikeData):SimilarBikesPage(bikeDataInput) {
    @Composable
    override fun show() {
        BackHandler {
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("bikeData",bikeDataInput)
            navController.popBackStack()
        }
        super.show()
    }
}