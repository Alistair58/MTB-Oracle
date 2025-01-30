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
import mtboracle.composeapp.generated.resources.MTB_Oracle_Bike_V3
import org.jetbrains.compose.resources.painterResource
import kotlin.reflect.typeOf

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        WebApp()
    }
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
                val homepage = WebHomepage(navController)
                homepage.ShowHomepage()
                //When you have just this destination and text it works
                //Images also work
            }
            /*composable<WebBrandModelYearScreen>(
                typeMap = mapOf(
                    typeOf<WebBikeData>() to WebBikeDataObject.bikeData
                )
            ){
                val args = it.toRoute<WebBrandModelYearScreen>()
                val form = WebBrandModelYearForm(navController,args.bikeData,args.isValuation)
                form.ShowForm()
            }
            composable<WebCategoryConditionCountryScreen>(
                typeMap = mapOf(
                    typeOf<WebBikeData>() to WebBikeDataObject.bikeData
                )
            ) {
                val args = it.toRoute<WebCategoryConditionCountryScreen>()
                val form = WebCategoryConditionCountryForm(navController, args.bikeData,args.isValuation)
                form.ShowForm()
            }
            composable<WebSizeMaterialTravelScreen>(
                typeMap = mapOf(
                    typeOf<BikeData>() to WebBikeDataObject.bikeData
                )
            ) {
                val args = it.toRoute<WebSizeMaterialTravelScreen>()
                val form = WebSizeMaterialTravelForm(navController, args.bikeData,args.isValuation)
                form.ShowForm()
            }*/
//            composable<ValuationScreen>{
//                val args = it.toRoute<ValuationScreen>()
//                val form = SizeMaterialTravelForm(navController,args.bikeData)
//                form.ShowForm()
//            }
        }
    }
}

@Serializable
object WebBikeDataObject {
    val bikeData = object : NavType<WebBikeData>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): WebBikeData? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): WebBikeData {
            return Json.decodeFromString(value)
        }

        override fun put(bundle: Bundle, key: String, value: WebBikeData) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: WebBikeData): String {
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
