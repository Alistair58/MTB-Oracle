package com.amhapps.mtboracle

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.navigation.NavController
import com.amhapps.mtboracle.screens.SimilarBikesPage

class AndroidSimilarBikesPage(private val navController: NavController,private val bikeDataInput:AndroidBikeData,private val context: Context):SimilarBikesPage(bikeDataInput) {
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

    override fun platformEbaySearcher(): EbaySearcher {
        return AndroidEbaySearcher(context)
    }
}