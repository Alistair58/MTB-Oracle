package com.amhapps.mtboracle

import BikeData
import kotlinx.serialization.Serializable


//TODO
// - check bike inputs e.g. for parts/
// - check what is being searched
// - Add country searching for eBay
// - Add currency exchange rates
// - Change warning messages for Similar Bikes
// - Bike sorting

//TODO
// - Non-crucial
// - see if navigation transition is changeable
// - app icon
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
