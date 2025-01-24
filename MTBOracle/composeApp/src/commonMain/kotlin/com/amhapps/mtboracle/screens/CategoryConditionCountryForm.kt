package com.amhapps.mtboracle.screens

import BikeData
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.amhapps.mtboracle.CompleteDropdown
import com.amhapps.mtboracle.MTBOracleTheme
import com.amhapps.mtboracle.SearchableDropdown
import com.amhapps.mtboracle.SizeMaterialTravelScreen

abstract class CategoryConditionCountryForm(private val navController: NavHostController, private val bikeData: BikeData,private val isValuation:Boolean) {
    private val countries = listOf(
        "Australia","Austria","Belgium","Canada","Switzerland","Germany","Spain", "France",
        "United Kingdom UK", "Ireland", "Italy", "Netherlands", "Poland", "United States of America USA")
    @Composable
    open fun ShowForm(){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ){

            var country by remember { mutableStateOf(bikeData.country) }
            SearchableDropdown(
                value = country,
                onValueChange = {country = it},
                onDropdownClick = {country = it},
                label = "Country",
                items = countries,
                modifier = Modifier.padding(0.dp,20.dp)
            )

            val categories = listOf("Trail","Enduro","XC","Downhill","E-bike","Gravel","Children's","Dirt Jump","Fat bike","Trials","Vintage")
            var category by remember { mutableStateOf(bikeData.category) }
            CompleteDropdown(
                value = category,
                onDropdownClick = {category = it},
                label = "Category",
                items = categories,
                iconContentDescription = "Category Dropdown",
                modifier = Modifier.padding(0.dp,20.dp)
            )



            val conditions = listOf("Brand new","Excellent","Good","Poor","For parts/unrideable","Other")
            var condition by remember { mutableStateOf(bikeData.condition) }
            CompleteDropdown(
                value = condition,
                onDropdownClick = {condition = it},
                label = "Condition",
                items = conditions,
                iconContentDescription = "Condition Dropdown",
                modifier = Modifier.padding(0.dp,20.dp)
            )

            NextButton(category,condition,country)
        }
    }

    @Composable
    abstract fun NextButton(category:String,condition:String,country:String)

}