package com.amhapps.mtboracle

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.amhapps.mtboracle.screens.SimilarBikesPage

class AndroidSimilarBikesPage(private val navController: NavController,private val bikeDataInput:AndroidBikeData,private val context: Context):SimilarBikesPage(navController,bikeDataInput) {
    @Composable
    override fun show(valuationPage:Boolean) {
        BackHandler {
            back()
        }
        super.show(false)
    }

    @Composable
    fun SubShow(){ //for use in valuation page
        super.show(true)
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
    @Composable
    override fun HomeButton(){
        Button(
            onClick = {
                navController.popBackStack(AndroidHomeScreen,false)
            },
            colors = MTBOracleTheme.buttonColors,
            modifier = Modifier
                .padding(0.dp, 30.dp)
                .width(150.dp)
                .height(50.dp)
        ){
            Text(
                "Home",
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}