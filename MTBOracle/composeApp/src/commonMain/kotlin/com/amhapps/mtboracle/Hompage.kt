package com.amhapps.mtboracle

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import mtboracle.composeapp.generated.resources.Res
import mtboracle.composeapp.generated.resources.transparent_mtb_oracle_bike_v2
import org.jetbrains.compose.resources.painterResource

open class Homepage(private var navController: NavController){
    @Composable
    fun ShowHomepage(){
        MTBOracleTheme{
            val colorStops = arrayOf(
                0.0f to Color.DarkGray,
                0.3f to MTBOracleTheme.colors.forest,
                1f to MTBOracleTheme.colors.forestLight
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ){
                BoxWithConstraints(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier
                        .background(Brush.verticalGradient(colorStops = colorStops))
                        .fillMaxWidth()
                )
                {
                    Text(text="MTB Oracle",
                        color= Color.White,
                        fontSize = 30.sp)
                    Column(modifier = Modifier.zIndex(2f)){
                        Spacer(modifier = Modifier
                            .height(30.dp)
                            .width(0.dp))
                        cartoonBike()

                    }
                    Column{
                        Spacer(modifier = Modifier
                            .height(150.dp)
                            .width(0.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .background(color = Color.White)
                                .fillMaxSize()){
                            Spacer(modifier = Modifier
                                .height(20.dp)
                                .width(0.dp))
                            HomepageBody()
                        }
                    }
                }
            }
        }

    }
    @Composable
    fun HomepageBody(){
        Text(text="Mountain Bike Valuations",
            color=Color.Black,
            fontSize = 20.sp)
        Button(
            onClick = {
                navController.navigate(BrandAndModelScreen)
            },
            colors = MTBOracleTheme.buttonColors,

            ){
            Text(text = "Value my bike", color = Color.White)
        }
    }

    @Composable
    open fun cartoonBike(){
        Image(
            painter = painterResource(Res.drawable.transparent_mtb_oracle_bike_v2),
            contentDescription = "Logo",
            modifier = Modifier
                .height(150.dp)
                .width(355.dp)
                .zIndex(3f) //Will be drawn on top of everything else
        )
    }
}