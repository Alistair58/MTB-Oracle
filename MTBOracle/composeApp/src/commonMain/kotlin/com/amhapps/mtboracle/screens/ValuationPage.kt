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
import androidx.navigation.navOptions
import com.amhapps.mtboracle.MTBOracleTheme
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
                                    Text(
                                        text =
                                        buildAnnotatedString {
                                            append("Brand: ")
                                            withStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = brandFontSize
                                                )
                                            ) {
                                                append(brandOutput)
                                            }
                                        },
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .padding(0.dp, 5.dp)
                                    )
                                    Text(
                                        text =
                                        buildAnnotatedString {
                                            append("Model: ")
                                            withStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = modelFontSize
                                                )
                                            ) {
                                                append(modelOutput)
                                            }
                                        },
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .padding(0.dp, 5.dp)
                                    )
                                    Text(
                                        text =
                                        buildAnnotatedString {
                                            append("Year: ")
                                            withStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold
                                                )
                                            ) {
                                                append(yearOutput)
                                            }
                                        },
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .padding(0.dp, 5.dp)
                                    )
                                    Text(
                                        text =
                                        buildAnnotatedString {
                                            append("Country: ")
                                            withStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = countryFontSize,
                                                )
                                            ) {
                                                append(countryOutput)
                                            }
                                        },
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .padding(0.dp, 5.dp)
                                    )
                                    Text(
                                        text =
                                        buildAnnotatedString {
                                            append("Category: ")
                                            withStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold
                                                )
                                            ) {
                                                append(bikeData.category)
                                            }
                                        },
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .padding(0.dp, 5.dp)
                                    )
                                    Text(
                                        text =
                                        buildAnnotatedString {
                                            append("Condition: ")
                                            withStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold
                                                )
                                            ) {
                                                append(bikeData.condition)
                                            }
                                        },
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .padding(0.dp, 5.dp)
                                    )

                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(0.5f)
                                ){
                                    Text(
                                        text =
                                        buildAnnotatedString {
                                            append("Size: ")
                                            withStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold
                                                )
                                            ) {
                                                append(bikeData.size)
                                            }
                                        },
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .padding(0.dp, 5.dp)
                                    )
                                    Text(
                                        text =
                                        buildAnnotatedString {
                                            append("Wheel Size: ")
                                            withStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold
                                                )
                                            ) {
                                                append(bikeData.wheelSize)
                                            }
                                        },
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .padding(0.dp, 5.dp)
                                    )
                                    Text(
                                        text =
                                        buildAnnotatedString {
                                            append("Material: ")
                                            withStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold
                                                )
                                            ) {
                                                append(bikeData.material)
                                            }
                                        },
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .padding(0.dp, 5.dp)
                                    )
                                    Text(
                                        text =
                                        buildAnnotatedString {
                                            append("Front Travel: ")
                                            withStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold
                                                )
                                            ) {
                                                append(frontSusOutput)
                                            }
                                        },
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .padding(0.dp, 5.dp)
                                    )
                                    Text(
                                        text =
                                        buildAnnotatedString {
                                            append("Rear Travel: ")
                                            withStyle(
                                                SpanStyle(
                                                    fontWeight = FontWeight.Bold
                                                )
                                            ) {
                                                append(rearSusOutput)
                                            }
                                        },
                                        fontSize = 18.sp,
                                        modifier = Modifier
                                            .padding(0.dp, 5.dp)
                                    )
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