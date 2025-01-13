package com.amhapps.mtboracle

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.navigation.NavController
import com.amhapps.mtboracle.screens.SimilarBikesPage

class AndroidSimilarBikesPage(private val navController: NavController,private val bikeDataInput:AndroidBikeData,private val context: Context):SimilarBikesPage(navController,bikeDataInput) {
    @Composable
    override fun show() {
        BackHandler {
            back()
        }
        super.show()
    }

    override fun platformEbaySearcher(): EbaySearcher {
        return AndroidEbaySearcher(context)
    }

    override fun platformHomeScreen(): Any {
        return AndroidHomeScreen
    }

    override fun back() {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set("bikeData",bikeDataInput)
        navController.popBackStack()
    }
}