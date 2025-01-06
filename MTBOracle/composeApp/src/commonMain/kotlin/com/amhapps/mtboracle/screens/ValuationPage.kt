package com.amhapps.mtboracle.screens

import BikeData
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.Arrangement.Horizontal
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.navigation.navOptions
import com.amhapps.mtboracle.MTBOracleTheme
import com.amhapps.mtboracle.SpecText
import mtboracle.composeapp.generated.resources.Res

abstract class ValuationPage(protected val navController: NavHostController, private val bikeData: BikeData){
    @Composable
    open fun show(){
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ){
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .padding(5.dp,5.dp,0.dp,30.dp)
                ){
                    Text("Price Estimation",
                        fontSize = 40.sp,
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(){
                        val output = "£" + valuation()
                        Text(
                            text = output,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(40.dp, 40.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(5.dp,0.dp)
                    ) {
                        val brandFontSize = if(bikeData.brand.length>15) 14.sp else 18.sp
                        val brandOutput = if(bikeData.brand.length>25) bikeData.brand.subSequence(0,25) else {
                            if (bikeData.brand.length > 12) {
                                "\n" + bikeData.brand
                            } else {
                                bikeData.brand
                            }
                        }
                        val modelFontSize = if(bikeData.model.length>15) 14.sp else 18.sp
                        val modelOutput = if(bikeData.model.length>25) bikeData.model.subSequence(0,25) else{
                            if(bikeData.model.length>12){
                                "\n"+bikeData.model
                            }
                            else{
                                bikeData.model
                            }

                        }
                        val countryFontSize = if(bikeData.country.length>15) 14.sp else 18.sp
                        val countryOutput = if(bikeData.country.length>25) bikeData.country.subSequence(0,25) else{
                            if(bikeData.country.length>12){
                                "\n"+bikeData.country
                            }
                            else{
                                bikeData.country
                            }

                        }
                        val yearOutput = if(bikeData.year in 0..2999) bikeData.year.toString() else ""

                        val frontSusOutput = if(bikeData.frontTravel >=0 && bikeData.frontTravel<1000) bikeData.frontTravel.toString()+"mm" else ""
                        val rearSusOutput = if(bikeData.rearTravel >=0 && bikeData.rearTravel<1000) bikeData.rearTravel.toString()+"mm" else ""

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(0.dp,0.dp,0.dp,30.dp)
                            ){
                            Text(
                                "Based on these inputs:",
                                fontSize = 25.sp,
                                modifier = Modifier
                                    .padding(0.dp, 10.dp)
                            )
                            Row(
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Column (
                                    modifier = Modifier
                                        .fillMaxWidth(0.5f)
                                ){
                                    SpecText("Brand: ",brandOutput.toString(),brandFontSize)
                                    SpecText("Model: ",modelOutput.toString(),modelFontSize)
                                    SpecText("Year: ",yearOutput)
                                    SpecText("Country: ", countryOutput.toString(),countryFontSize)
                                    SpecText("Category: ",bikeData.category)
                                    SpecText("Condition: ",bikeData.condition)
                                    SpecText("Size: ",bikeData.size)

                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(0.5f)
                                ){
                                    SpecText("Wheel Size: ",bikeData.wheelSize)
                                    SpecText("Material: ",bikeData.material)
                                    SpecText("Front Travel: ",frontSusOutput)
                                    SpecText("Rear Travel: ",rearSusOutput)
                                }
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier.align(Alignment.BottomCenter)
            ){
                HomeButton()
            }
        }



    }
    abstract fun valuation():Float
    @Composable
    abstract fun HomeButton()


}