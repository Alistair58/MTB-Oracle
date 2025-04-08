package com.amhapps.mtboracle

import BikeData
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.amhapps.mtboracle.screens.Homepage
import mtboracle.composeapp.generated.resources.Res
import mtboracle.composeapp.generated.resources.MTB_Oracle_Bike_V3
import org.jetbrains.compose.resources.painterResource

class WebHomepage(private var navController: NavController) : Homepage(navController){
    @Composable
    override fun ValuationButton() {
        Button(
            onClick = {
                navController.navigate(
                    WebBrandModelYearScreen(bikeData = WebBikeData(), true)
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
        TODO("Not yet implemented")
    }

    @Composable
    override fun RecentBikes() {
        //Web doesn't have recent bikes currently
    }

    @Composable
    override fun cartoonBike(){
        Image(
            painter = painterResource(Res.drawable.MTB_Oracle_Bike_V3),
            contentDescription = "Logo",
            modifier = Modifier
                .height(300.dp) //Bigger than the mobile one - so that it overlaps properly
                .width(355.dp)
                .zIndex(3f) //Will be drawn on top of everything else
        )
    }

    override suspend fun removeRecentBike(bikeData: BikeData, onBikeDelete: (BikeData) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun platformValuationScreen(bikeData: BikeData): Any {
        return WebValuationScreen
    }

    override fun platformSimilarBikesScreen(bikeData: BikeData): Any {
        return WebSimilarBikesScreen
    }
}