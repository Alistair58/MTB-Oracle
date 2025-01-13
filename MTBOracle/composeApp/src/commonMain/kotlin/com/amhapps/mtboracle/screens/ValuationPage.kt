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
import androidx.compose.foundation.layout.Spacer
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
import com.amhapps.mtboracle.BikeInputDisplay
import com.amhapps.mtboracle.DropdownText
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
                        BikeInputDisplay(bikeData)
                    }
                    Row{

                        Column(modifier = Modifier.fillMaxWidth(0.8f),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Spacer(modifier = Modifier.height(20.dp))
                            DropdownText(title = "Note About Accuracy",
                                body = "The price given is only an estimate. "+
                                        "\n\nMTB Oracle uses a machine learning model to predict a price for your mountain bike. "+
                                        "The model is not 100% accurate and does make mistakes. "+
                                        "Errors could come from:\n" +
                                        " • Rare bikes\n"+
                                        " • Lack of info about component upgrades to the bike\n"+
                                        " • Lack of info about bike maintenance\n"+
                                                " • Visual factors\n\n"+
                                        "The only information about your bike that the model is given are the inputs you entered. "+
                                        "If your information is incorrect, the valuation will be incorrect. "+
                                        "E.g. putting in no values gives a price of 2000 GBP as this is the median used MTB price.")
                            Spacer(modifier = Modifier.height(100.dp)) //Stops the text going under the home button
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