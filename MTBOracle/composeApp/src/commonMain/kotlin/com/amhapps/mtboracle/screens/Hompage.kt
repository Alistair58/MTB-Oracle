package com.amhapps.mtboracle.screens

import BikeData
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.amhapps.mtboracle.MTBOracleTheme
import com.amhapps.mtboracle.Platform
import com.amhapps.mtboracle.ValuationScreen
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mtboracle.composeapp.generated.resources.Res
import mtboracle.composeapp.generated.resources.MTB_Oracle_Bike_V3
import mtboracle.composeapp.generated.resources.binIcon
import org.jetbrains.compose.resources.painterResource

abstract class Homepage(private var navController: NavController){
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
                        .fillMaxWidth()
                )
                {
                    Column (modifier = Modifier
                        .background(Brush.verticalGradient(colorStops = colorStops))
                        .height(200.dp)
                        .fillMaxWidth()
                    ){} //Matches the spacer
                    Text(text="MTB Oracle",
                        color= Color.White,
                        fontSize = 40.sp,
                        modifier = Modifier.zIndex(2f))
                    Column(modifier = Modifier.zIndex(2f)){
                        Spacer(modifier = Modifier
                            .height(40.dp)
                            .width(0.dp))
                        cartoonBike()

                    }
                    Column{
                        Spacer(modifier = Modifier
                            .height(200.dp)
                            .width(0.dp)) //Box means everything would overlap otherwise
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .background(color = Color.White)
                                .fillMaxSize()
                                .padding(5.dp,0.dp)){
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
            fontSize = 30.sp)
        Spacer(modifier = Modifier
            .height(40.dp)
            .width(0.dp))
        ValuationButton()
        Spacer(modifier = Modifier
            .height(20.dp)
            .width(0.dp))
        SimilarBikesButton()
        Spacer(modifier = Modifier
            .height(20.dp)
            .width(0.dp))
        RecentBikes()

    }

    @Composable
    abstract fun ValuationButton()

    @Composable
    abstract  fun SimilarBikesButton()

    @Composable
    abstract fun RecentBikes()





    @Composable
    open fun cartoonBike(){
        Image(
            painter = painterResource(Res.drawable.MTB_Oracle_Bike_V3),
            contentDescription = "Logo",
            modifier = Modifier
                .height(200.dp)
                .width(473.dp)
                .zIndex(3f) //Will be drawn on top of everything else
        )
    }

    @Composable
    open fun RecentBike(bikeData: BikeData,onBikeDelete:(BikeData)->Unit){
        var removeBike by remember { mutableStateOf(false) }
        Row(
            modifier = Modifier
                .padding(40.dp, 5.dp)
                .background(
                    color = MTBOracleTheme.colors.lightGrey,
                    shape = RoundedCornerShape(5.dp)
                )
        ) {
            Column(modifier = Modifier.padding(5.dp)) {
                val brandOutput = if (bikeData.brand.length > 25)
                    bikeData.brand.subSequence(0, 25)
                else bikeData.brand
                val modelOutput = if (bikeData.model.length > 25)
                    bikeData.model.subSequence(0, 25)
                else bikeData.model
                val countryOutput: String = if (bikeData.country.length > 25)
                    bikeData.country.substring(0, 25)
                else bikeData.country
                val yearOutput = if (bikeData.year in 0..2999) bikeData.year.toString() else ""

                val frontSusOutput =
                    if (bikeData.frontTravel >= 0 && bikeData.frontTravel < 1000) bikeData.frontTravel.toInt()
                        .toString() + "mm" else ""
                val rearSusOutput =
                    if (bikeData.rearTravel >= 0 && bikeData.rearTravel < 1000) bikeData.rearTravel.toInt()
                        .toString() + "mm" else ""


                if (bikeData.price.isNotEmpty()) Text(
                    text = bikeData.price,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$yearOutput $brandOutput $modelOutput",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                var infoString = ""
                if (bikeData.size.isNotEmpty()) infoString += bikeData.size + ", "
                if (bikeData.condition.isNotEmpty()) infoString += bikeData.condition + ", "
                if (bikeData.material.isNotEmpty()) infoString += bikeData.material + ", "
                if (bikeData.country.isNotEmpty()) infoString += countryOutput + ", "
                if (bikeData.category.isNotEmpty()) infoString += bikeData.category + ", "
                if (bikeData.wheelSize.isNotEmpty()) infoString += bikeData.wheelSize + ", "
                if (frontSusOutput.isNotEmpty()) infoString += "Front: ${frontSusOutput}, "
                if (rearSusOutput.isNotEmpty()) infoString += "Rear: ${rearSusOutput}, "

                if (infoString.isNotEmpty()) Text(
                    text = infoString.substring(
                        0,
                        infoString.length - 2
                    )
                ) //Cut off the last ", "


                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = { navController.navigate(platformValuationScreen(bikeData)) },
                        colors = if (bikeData.price == "")
                            MTBOracleTheme.buttonColors
                        else ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                        modifier = Modifier.padding(5.dp, 0.dp)

                    ) {
                        Text(
                            "Value",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                    Button(
                        onClick = { navController.navigate(platformSimilarBikesScreen(bikeData)) },
                        colors = MTBOracleTheme.buttonColors,
                        modifier = Modifier.padding(5.dp, 0.dp)
                    ) {
                        Text(
                            "Similar Bikes",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                    Image(
                        painter = painterResource(Res.drawable.binIcon),
                        contentDescription = "Delete",
                        modifier = Modifier
                            .clickable { removeBike = true;println("pressed remove") }
                            .width(25.dp)
                    )
                }
            }

        }
        if(removeBike){
            val scope = rememberCoroutineScope()
            LaunchedEffect(true) {
                scope.launch{
                    removeRecentBike(bikeData, onBikeDelete = {bikeData -> onBikeDelete(bikeData);removeBike=false})
                    //Lambda so we don't keep on calling this and so it actually runs in first place
                }
            }
            println("removed bike")
        }

    }

    abstract suspend fun removeRecentBike(bikeData: BikeData,onBikeDelete:(BikeData)->Unit)

    abstract fun platformValuationScreen(bikeData: BikeData):Any

    abstract fun platformSimilarBikesScreen(bikeData: BikeData):Any
}