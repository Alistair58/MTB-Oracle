package com.amhapps.mtboracle

import BikeData
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.amhapps.mtboracle.screens.Homepage
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = applicationContext
        setContent {
            AndroidApp(context)
        }
    }

    @Composable
    fun AndroidApp(context: Context) {
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
                composable<BrandModelYearScreen> (
                    typeMap = mapOf(
                        typeOf<BikeData>() to BikeDataObject.bikeData
                    )
                ){
                    val prevBikeData = it.savedStateHandle.get<BikeData>("bikeData")
                    val args = it.toRoute<BrandModelYearScreen>()
                    val bikeData = prevBikeData ?: args.bikeData
                    val form = AndroidBrandAndModelForm(navController, bikeData)
                    form.ShowForm()
                }
                composable<CategoryConditionCountryScreen>(
                    typeMap = mapOf(
                        typeOf<BikeData>() to BikeDataObject.bikeData
                    )
                ) {
                    val prevBikeData = it.savedStateHandle.get<BikeData>("bikeData")
                    val args = it.toRoute<CategoryConditionCountryScreen>()
                    val bikeData = prevBikeData ?: args.bikeData
                    val form = AndroidCategoryCountryConditionForm(navController, bikeData)

                    form.ShowForm()
                }
                composable<SizeMaterialTravelScreen>(
                    typeMap = mapOf(
                        typeOf<BikeData>() to BikeDataObject.bikeData
                    )
                ) {
                    val prevBikeData = it.savedStateHandle.get<BikeData>("bikeData")
                    val args = it.toRoute<SizeMaterialTravelScreen>()
                    val bikeData = prevBikeData ?: args.bikeData
                    val form = AndroidSizeMaterialTravelForm(navController, bikeData)
                    form.ShowForm()
                }
                composable<ValuationScreen>(
                    typeMap = mapOf(
                        typeOf<BikeData>() to BikeDataObject.bikeData
                    )
                ) {
                    val args = it.toRoute<ValuationScreen>()
                    val page = AndroidValuationPage(navController, args.bikeData,context)
                    page.show()
                }
            }
        }
    }

//    @Preview
//    @Composable
//    fun AppAndroidPreview() {
//        AndroidApp()
//    }
}







