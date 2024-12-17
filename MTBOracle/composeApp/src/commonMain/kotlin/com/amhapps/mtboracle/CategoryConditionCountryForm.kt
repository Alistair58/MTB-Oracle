package com.amhapps.mtboracle

import Bike
import BikeData
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController

class CategoryConditionCountryForm(private val navController: NavHostController,private val bikeData: BikeData) {
    @Composable
    fun ShowForm(){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ){
            val categories = listOf("Trail","Enduro","XC","Downhill","E-bike","Gravel","Children's","Dirt Jump","Fat bike","Trials","Vintage")
            var category by remember { mutableStateOf("") }
            CompleteDropdown(
                value = category,
                onValueChange = {category = it},
                onDropdownClick = {category = it},
                label = "Category",
                items = categories,
                iconContentDescription = "Category Dropdown",
                modifier = Modifier.padding(0.dp,20.dp)
            )



            val conditions = listOf("Brand new","Excellent","Good","Poor","For parts/unrideable","Other")
            var condition by remember { mutableStateOf("") }
            CompleteDropdown(
                value = condition,
                onValueChange = {condition = it},
                onDropdownClick = {condition = it},
                label = "Condition",
                items = conditions,
                iconContentDescription = "Condition Dropdown",
                modifier = Modifier.padding(0.dp,20.dp)
            )

            val countries = listOf("Afghanistan","Albania","Algeria","Andorra","Angola","Antigua and Barbuda","Argentina","Armenia",
                "Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin",
                "Bhutan","Bolivia","Bosnia and Herzegovina","Botswana","Brazil","Brunei","Bulgaria","Burkina Faso","Burundi","Cabo Verde",
                "Cambodia","Cameroon","Canada","Central African Republic","Chad","Chile","China","Colombia","Comoros","Congo (Congo-Brazzaville)",
                "Costa Rica","Croatia","Cuba","Cyprus","Czechia (Czech Republic)","Democratic Republic of the Congo","Denmark","Djibouti","Dominica",
                "Dominican Republic","Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Eswatini (fmr. Swaziland)","Ethiopia",
                "Fiji","Finland","France","Gabon","Gambia","Georgia","Germany","Ghana","Greece","Grenada","Guatemala","Guinea","Guinea-Bissau","Guyana",
                "Haiti","Holy See","Honduras","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Israel","Italy","Jamaica","Japan","Jordan",
                "Kazakhstan","Kenya","Kiribati","Kosovo","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania",
                "Luxembourg","Madagascar","Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands","Mauritania","Mauritius","Mexico","Micronesia",
                "Moldova","Monaco","Mongolia","Montenegro","Morocco","Mozambique","Myanmar","Namibia","Nauru","Nepal","Netherlands","New Zealand","Nicaragua",
                "Niger","Nigeria","North Korea","North Macedonia","Norway","Oman","Pakistan","Palau","Palestine","Panama","Papua New Guinea","Paraguay",
                "Peru","Philippines","Poland","Portugal","Qatar","Romania","Russia","Rwanda","Saint Kitts and Nevis","Saint Lucia","Saint Vincent and the Grenadines",
                "Samoa","San Marino","Sao Tome and Principe","Saudi Arabia","Senegal","Serbia","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia",
                "Solomon Islands","Somalia","South Africa","South Korea","South Sudan","Spain","Sri Lanka","Sudan","Suriname","Sweden","Switzerland","Syria",
                "Taiwan","Tajikistan","Tanzania","Thailand","Timor-Leste","Togo","Tonga","Trinidad and Tobago","Tunisia","Turkey","Turkmenistan","Tuvalu","Uganda","Ukraine",
                "United Arab Emirates","United Kingdom","United States of America (USA)","Uruguay","Uzbekistan","Vanuatu","Venezuela","Vietnam","Western Sahara","Yemen","Zambia","Zimbabwe"
            )
            var country by remember { mutableStateOf("") }
            SearchableDropdown(
                value = country,
                onValueChange = {country = it},
                onDropdownClick = {country = it},
                label = "Country",
                items = countries,
                iconContentDescription = "Country Dropdown",
                modifier = Modifier.padding(0.dp,20.dp)
            )

            Button(
                onClick = {
                    bikeData.category = category
                    bikeData.condition = condition
                    bikeData.country = country
                    navController.navigate(SizeMaterialTravelScreen(bikeData))
                },
                colors = MTBOracleTheme.buttonColors,
                modifier = Modifier
                    .padding(0.dp,30.dp)
                    .height(50.dp)
                    .width(100.dp)
                ){
                Text(text = "Next",
                    color = Color.White,
                    fontSize = 20.sp)
            }
        }
    }
}