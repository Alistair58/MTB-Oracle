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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch
import mtboracle.composeapp.generated.resources.Res
import kotlin.math.round

abstract class ValuationPage(protected val navController: NavHostController, private var bikeData: BikeData){
    private val nnWeight = 0.6f

    @Composable
    abstract fun show()


    @Composable
    open fun body(exchangeRate:Float,similarBikesMedian:Float){
        var cached by remember { mutableStateOf(false) }
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(5.dp,5.dp,0.dp,5.dp)
        ){
            Text("Price Estimation",
                fontSize = 40.sp,
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(){
                println("Similar bikes median: $similarBikesMedian")
                val nnValuation = valuation()
                val valuation:Int = if(similarBikesMedian > 0f) {
                    (round((nnWeight * nnValuation) * exchangeRate) + (1 - nnWeight) * similarBikesMedian).toInt() //SimilarBikesMedian is already in local currency
                }
                else{
                    (round(nnValuation* exchangeRate)).toInt()
                }


                val currencySymbols = currencySymbol(bikeData.country)
                val output = if(exchangeRate<0f) "" else "${currencySymbols[0]}$valuation${currencySymbols[1]}"
                bikeData.price = output
                if(!cached && (similarBikesMedian>0f || similarBikesMedian==-2f)){ //-2f if there are no similar bikes
                    println("Not cached")
                    val scope = rememberCoroutineScope()
                    LaunchedEffect(true){
                        scope.launch {
                            println("Caching")
                            cacheBike(bikeData)
                            cached = true
                        }
                    }


                }
                Text(
                    text = output,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(20.dp, 10.dp)
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
                    //Spacer(modifier = Modifier.height(10.dp))
                    DropdownText(title = "Note About Accuracy",
                        body = "The price given is only an estimate. "+
                                "\n\nMTB Oracle uses a machine learning model along with similar bikes to predict a price for your mountain bike. "+
                                "The model is not 100% accurate and does make mistakes. "+
                                "Errors could come from:\n" +
                                " • Rare bikes\n"+
                                " • Lack of info about component upgrades to the bike\n"+
                                " • Lack of info about bike maintenance\n"+
                                " • Visual factors\n\n"+
                                "The only information about your bike that the model is given are the inputs you entered. "+
                                "If your information is incorrect, the valuation will be incorrect. "+
                                "E.g. putting in no values gives a price of 2000 GBP as this is the median used MTB price.")
                    //Spacer(modifier = Modifier.height(20.dp))
                }
            }

        }
    }
    abstract fun valuation():Float
    @Composable
    abstract fun HomeButton()

    fun countryToCurrency(country:String):String{
        return when(country){
            "Australia" -> "AUD"
            "Austria" -> "EUR"
            "Belgium" -> "EUR"
            "Germany" -> "EUR"
            "Spain" -> "EUR"
            "France" -> "EUR"
            "Ireland" -> "EUR"
            "Italy" -> "EUR"
            "Netherlands" -> "EUR"
            "Canada" -> "CAD"
            "Switzerland" -> "CHF"
            "United Kingdom UK" -> "GBP"
            "Hong Kong" -> "HKD"
            "Poland" -> "PLN"
            "Singapore" -> "SGD"
            else -> "USD"
        }
    }

    private fun currencySymbol(country: String):List<String>{
        return when(country){
            "Australia" -> listOf("AU$","")
            "Austria" -> listOf("€","")
            "Belgium" -> listOf("€","")
            "Germany" -> listOf("€","")
            "Spain" -> listOf("€","")
            "France" -> listOf("€","")
            "Ireland" -> listOf("€","")
            "Italy" -> listOf("€","")
            "Netherlands" -> listOf("€","")
            "Canada" -> listOf("CA$","")
            "Switzerland" -> listOf(""," CHF")
            "United Kingdom UK" -> listOf("£","")
            "Hong Kong" -> listOf("HK$","")
            "Poland" -> listOf(""," zł")
            "Singapore" -> listOf("S$","")
            else -> listOf("US$","")
        }
    }

    protected abstract suspend fun cacheBike(bikeData: BikeData)



}