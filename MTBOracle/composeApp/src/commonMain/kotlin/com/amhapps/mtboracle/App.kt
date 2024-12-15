package com.amhapps.mtboracle

import Bike
import BikeData
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.bundle.Bundle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import mtboracle.composeapp.generated.resources.Res
import mtboracle.composeapp.generated.resources.compose_multiplatform
import mtboracle.composeapp.generated.resources.mtb_oracle_bike_v1
import mtboracle.composeapp.generated.resources.transparent_mtb_oracle_bike_v2
import kotlin.reflect.typeOf

//TODO
// - finish form and make look pretty
// - see if navigation transition is changeable
// - app icon
// - make input boxes look pretty


@Composable
fun App(web:Boolean){
    MTBOracleTheme{
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = HomeScreen
        ){
            composable<HomeScreen>{
                val homepage = if (web) WebHomepage(navController) else Homepage(navController)
                homepage.ShowHomepage()
            }
            composable<BrandModelYearScreen>{
                val form = BrandAndModelForm(navController)
                form.ShowForm()
            }
            composable<CategoryConditionCountryScreen>(
                typeMap = mapOf(
                    typeOf<BikeData>() to BikeDataObject.bikeData
                )
            ){
                val args = it.toRoute<CategoryConditionCountryScreen>()
                val form = CategoryConditionCountryForm(navController,args.bikeData)
                form.ShowForm()
            }
            composable<SizeMaterialTravelScreen>(
                typeMap = mapOf(
                    typeOf<BikeData>() to BikeDataObject.bikeData
                )
            ){
                val args = it.toRoute<SizeMaterialTravelScreen>()
                val form = SizeMaterialTravelForm(navController,args.bikeData)
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
object HomeScreen

@Serializable
object BrandModelYearScreen

@Serializable
object BikeDataObject {
    val bikeData = object : NavType<BikeData>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): BikeData? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): BikeData {
            return Json.decodeFromString(value) //Uri again
        }

        override fun put(bundle: Bundle, key: String, value: BikeData) {
            bundle.putString(key,Json.encodeToString(value))
        }

        override fun serializeAsValue(value: BikeData): String {
            return Json.encodeToString(value) //I think it should have Uri but that's an Android thing
        }

    }
}


@Serializable
data class CategoryConditionCountryScreen(
    val bikeData: BikeData
)

@Serializable
data class SizeMaterialTravelScreen(
    val bikeData: BikeData
)

@Serializable
data class ValuationScreen(
    val bikeData: BikeData
)

