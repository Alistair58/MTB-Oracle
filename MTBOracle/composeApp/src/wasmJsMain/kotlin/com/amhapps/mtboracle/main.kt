package com.amhapps.mtboracle

import BikeData
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.ComposeViewport
import androidx.core.bundle.Bundle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.browser.document
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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
            startDestination = HomeScreen
        ) {
            composable<HomeScreen> {
                val homepage = WebHomepage(navController)
                homepage.ShowHomepage()
            }
            composable<BrandModelYearScreen> {
                val form = BrandAndModelForm(navController)
                form.ShowForm()
            }
            composable<CategoryConditionCountryScreen>(
                typeMap = mapOf(
                    typeOf<BikeData>() to BikeDataObject.bikeData
                )
            ) {
                val args = it.toRoute<CategoryConditionCountryScreen>()
                val form = CategoryConditionCountryForm(navController, args.bikeData)
                form.ShowForm()
            }
            composable<SizeMaterialTravelScreen>(
                typeMap = mapOf(
                    typeOf<BikeData>() to BikeDataObject.bikeData
                )
            ) {
                val args = it.toRoute<SizeMaterialTravelScreen>()
                val form = SizeMaterialTravelForm(navController, args.bikeData)
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