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
import kotlin.reflect.typeOf

//TODO
// -add notes about accuracy
// -refactor output page to make neater
// - check bike inputs e.g. for parts/
// - see if navigation transition is changeable
// - app icon
// - eBay API


//Refactor list
// - duplication of neural network files for android and for wasm


@Serializable
object HomeScreen

@Serializable
data class BrandModelYearScreen(
    val bikeData: BikeData
)

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
