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
import kotlinx.serialization.Serializable
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
                startDestination = AndroidHomeScreen
            ) {
                composable<AndroidHomeScreen> {
                    val homepage = AndroidHomepage(navController)
                    homepage.ShowHomepage()
                }
                composable<AndroidBrandModelYearScreen> (
                    typeMap = mapOf(
                        typeOf<AndroidBikeData>() to AndroidBikeDataObject.bikeData
                    )
                ){
                    val prevBikeData = it.savedStateHandle.get<AndroidBikeData>("bikeData")
                    val args = it.toRoute<AndroidBrandModelYearScreen>()
                    val bikeData = prevBikeData ?: args.bikeData
                    val form = AndroidBrandModelYearForm(navController, bikeData)
                    form.ShowForm()
                }
                composable<AndroidCategoryConditionCountryScreen>(
                    typeMap = mapOf(
                        typeOf<AndroidBikeData>() to AndroidBikeDataObject.bikeData
                    )
                ) {
                    val prevBikeData = it.savedStateHandle.get<AndroidBikeData>("bikeData")
                    val args = it.toRoute<AndroidCategoryConditionCountryScreen>()
                    val bikeData = prevBikeData ?: args.bikeData
                    val form = AndroidCategoryCountryConditionForm(navController, bikeData)

                    form.ShowForm()
                }
                composable<AndroidSizeMaterialTravelScreen>(
                    typeMap = mapOf(
                        typeOf<AndroidBikeData>() to AndroidBikeDataObject.bikeData
                    )
                ) {
                    val prevBikeData = it.savedStateHandle.get<AndroidBikeData>("bikeData")
                    val args = it.toRoute<AndroidSizeMaterialTravelScreen>()
                    val bikeData = prevBikeData ?: args.bikeData
                    val form = AndroidSizeMaterialTravelForm(navController, bikeData)
                    form.ShowForm()
                }
                composable<AndroidValuationScreen>(
                    typeMap = mapOf(
                        typeOf<AndroidBikeData>() to AndroidBikeDataObject.bikeData
                    )
                ) {
                    val args = it.toRoute<AndroidValuationScreen>()
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
@Serializable
object AndroidHomeScreen
@Serializable
data class AndroidBrandModelYearScreen(
    val bikeData: AndroidBikeData
)

@Serializable
data class AndroidCategoryConditionCountryScreen(
    val bikeData: AndroidBikeData
)

@Serializable
data class AndroidSizeMaterialTravelScreen(
    val bikeData: AndroidBikeData
)

@Serializable
data class AndroidValuationScreen(
    val bikeData: AndroidBikeData
)






