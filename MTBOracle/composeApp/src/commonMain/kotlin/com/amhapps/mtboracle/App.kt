package com.amhapps.mtboracle

import Bike
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import mtboracle.composeapp.generated.resources.Res
import mtboracle.composeapp.generated.resources.compose_multiplatform
import mtboracle.composeapp.generated.resources.mtb_oracle_bike_v1
import mtboracle.composeapp.generated.resources.transparent_mtb_oracle_bike_v2

//TODO
// - finish the dropdown menu
// - class for inputs e.g. bikeInputs
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
            composable<CategoryConditionCountryScreen>{
                val args = it.toRoute<CategoryConditionCountryScreen>()
                val form = CategoryConditionCountryForm(navController,args.bike)
                form.ShowForm()
            }
            composable<SizeMaterialTravelScreen>{
                val args = it.toRoute<SizeMaterialTravelScreen>()
                val form = SizeMaterialTravelForm(navController,args.bike)
                form.ShowForm()
            }
            composable<ValuationScreen>{
                val args = it.toRoute<ValuationScreen>()
                val form = SizeMaterialTravelForm(navController,args.bike)
                form.ShowForm()
            }
        }
    }

}

@Serializable
object HomeScreen

@Serializable
object BrandModelYearScreen


@Serializable
data class CategoryConditionCountryScreen(
    @Contextual
    val bike:Bike
)

@Serializable
data class SizeMaterialTravelScreen(
    @Contextual
    val bike:Bike
)

@Serializable
data class ValuationScreen(
    @Contextual
    val bike:Bike
)

