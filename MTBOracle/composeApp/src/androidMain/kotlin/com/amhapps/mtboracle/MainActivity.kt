package com.amhapps.mtboracle

import BikeData
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mtboracle.composeapp.generated.resources.Res
import mtboracle.composeapp.generated.resources.mtb_oracle_bike_v1
import org.jetbrains.compose.resources.painterResource
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidApp()
        }
    }
}

@Composable
fun AndroidApp(){
    MTBOracleTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = HomeScreen
        ) {
            composable<HomeScreen> {
                val homepage = Homepage(navController)
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

@Preview
@Composable
fun AppAndroidPreview() {
    AndroidApp()
}


@Serializable
object BikeDataObject {
    val bikeData = object : NavType<BikeData>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): BikeData? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): BikeData {
            return Json.decodeFromString(Uri.decode(value)) //Uri again
        }

        override fun put(bundle: Bundle, key: String, value: BikeData) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: BikeData): String {
            return Uri.encode(Json.encodeToString(value)) //Uri is only an Android thing
        }

    }
}





