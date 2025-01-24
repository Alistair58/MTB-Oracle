package com.amhapps.mtboracle

import BikeData
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.ComposeViewport
import androidx.compose.ui.zIndex
import androidx.core.bundle.Bundle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.amhapps.mtboracle.screens.BrandModelYearForm
import com.amhapps.mtboracle.screens.CategoryConditionCountryForm
import com.amhapps.mtboracle.screens.SizeMaterialTravelForm
import kotlinx.browser.document
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mtboracle.composeapp.generated.resources.Res
import mtboracle.composeapp.generated.resources.transparent_mtb_oracle_bike_v2
import org.jetbrains.compose.resources.painterResource
import kotlin.reflect.typeOf

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        WebApp()
    }
}

@Composable
fun temp(){
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
                    Image(
                        painter = painterResource(Res.drawable.transparent_mtb_oracle_bike_v2),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .height(300.dp) //Bigger than the mobile one - so that it overlaps properly
                            .width(355.dp)
                            .zIndex(3f) //Will be drawn on top of everything else
                    )

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
    //ValuationButton()
    Spacer(modifier = Modifier
        .height(20.dp)
        .width(0.dp))
    //SimilarBikesButton()
    Spacer(modifier = Modifier
        .height(20.dp)
        .width(0.dp))
    //RecentBikes()

}

@Composable
fun WebApp(){
    MTBOracleTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = WebHomeScreen
        ) {
            composable<WebHomeScreen> {
                Text("Navigation,text and images work", color = Color.Black)
                //TODO navigation is the problems
                //val homepage = WebHomepage(navController)
                //homepage.ShowHomepage()
            }
            composable<WebBrandModelYearScreen> {
                val args = it.toRoute<WebBrandModelYearScreen>()
                val form = WebBrandModelYearForm(navController,args.bikeData,args.isValuation)
                form.ShowForm()
            }
            composable<WebCategoryConditionCountryScreen>(
                typeMap = mapOf(
                    typeOf<BikeData>() to BikeDataObject.bikeData
                )
            ) {
                val args = it.toRoute<WebCategoryConditionCountryScreen>()
                val form = WebCategoryConditionCountryForm(navController, args.bikeData,args.isValuation)
                form.ShowForm()
            }
            composable<WebSizeMaterialTravelScreen>(
                typeMap = mapOf(
                    typeOf<BikeData>() to BikeDataObject.bikeData
                )
            ) {
                val args = it.toRoute<WebSizeMaterialTravelScreen>()
                val form = WebSizeMaterialTravelForm(navController, args.bikeData,args.isValuation)
                form.ShowForm()
            }
//            composable<ValuationScreen>{
//                val args = it.toRoute<ValuationScreen>()
//                val form = SizeMaterialTravelForm(navController,args.bikeData)
//                form.ShowForm()
//            }
        }
    }
}

@Serializable
object BikeDataObject {
    val bikeData = object : NavType<BikeData>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): BikeData? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): BikeData {
            return Json.decodeFromString(value)
        }

        override fun put(bundle: Bundle, key: String, value: BikeData) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: BikeData): String {
            return Json.encodeToString(value)
        }

    }
}

@Serializable
object WebHomeScreen
@Serializable
data class WebBrandModelYearScreen(
    val bikeData: WebBikeData,
    val isValuation:Boolean //Is it a valuation or a similar bike search
)

@Serializable
data class WebCategoryConditionCountryScreen(
    val bikeData: WebBikeData,
    val isValuation:Boolean
)

@Serializable
data class WebSizeMaterialTravelScreen(
    val bikeData: WebBikeData,
    val isValuation:Boolean
)

@Serializable
data class WebValuationScreen(
    val bikeData: WebBikeData
)

@Serializable
data class WebSimilarBikesScreen(
    val bikeData: WebBikeData
)
