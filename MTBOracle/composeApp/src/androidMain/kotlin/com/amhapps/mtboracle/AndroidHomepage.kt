package com.amhapps.mtboracle

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.amhapps.mtboracle.screens.Homepage

class AndroidHomepage(private val navController: NavController):Homepage(navController) {
    @Composable
    override fun ValuationButton() {
        Button(
            onClick = {
                navController.navigate(
                    AndroidBrandModelYearScreen(bikeData = AndroidBikeData(),true)
                )
            },
            colors = MTBOracleTheme.buttonColors,
            ){
            Text(text = "Value my bike",
                color = Color.White,
                fontSize = 30.sp)
        }
    }

    @Composable
    override fun SimilarBikesButton() {
        Button(
            onClick = {
                navController.navigate(
                    AndroidBrandModelYearScreen(bikeData = AndroidBikeData(),false)
                )
            },
            colors = MTBOracleTheme.buttonColors,
        ){
            Text(text = "See Similar Bikes",
                color = Color.White,
                fontSize = 30.sp)
        }
    }

}
//