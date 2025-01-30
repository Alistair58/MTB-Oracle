package com.amhapps.mtboracle

import BikeData
import kotlinx.serialization.Serializable


//TODO
// - check bike inputs e.g. for parts/
// - check what is being searched

//TODO
// - Non-crucial
// - hide eBay API key


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
