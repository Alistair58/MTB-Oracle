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

abstract class CategoryConditionCountryForm(private val navController: NavHostController, private val bikeData: BikeData,private val valuation:Boolean) {
    @Composable
    open fun ShowForm(){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ){
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
                "Niger","Nigeria","Northern Ireland","North Korea","North Macedonia","Norway","Oman","Pakistan","Palau","Palestine","Panama","Papua New Guinea","Paraguay",
                "Peru","Philippines","Poland","Portugal","Qatar","Romania","Russia","Rwanda","Saint Kitts and Nevis","Saint Lucia","Saint Vincent and the Grenadines",
                "Samoa","San Marino","Sao Tome and Principe","Saudi Arabia","Scotland","Senegal","Serbia","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia",
                "Solomon Islands","Somalia","South Africa","South Korea","South Sudan","Spain","Sri Lanka","Sudan","Suriname","Sweden","Switzerland","Syria",
                "Taiwan","Tajikistan","Tanzania","Thailand","Timor-Leste","Togo","Tonga","Trinidad and Tobago","Tunisia","Turkey","Turkmenistan","Tuvalu","Uganda","Ukraine",
                "United Arab Emirates","United Kingdom UK","United States of America USA","Uruguay","Uzbekistan","Vanuatu","Venezuela","Vietnam","Western Sahara","Wales","Yemen","Zambia","Zimbabwe"
            )
            var country by remember { mutableStateOf(bikeData.country) }
            SearchableDropdown(
                value = country,
                onValueChange = {country = it},
                onDropdownClick = {country = it},
                label = "Country",
                items = countries,
                iconContentDescription = "Country Dropdown",
                modifier = Modifier.padding(0.dp,20.dp)
            )

            val categories = listOf("Trail","Enduro","XC","Downhill","E-bike","Gravel","Children's","Dirt Jump","Fat bike","Trials","Vintage")
            var category by remember { mutableStateOf(bikeData.category) }
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
            var condition by remember { mutableStateOf(bikeData.condition) }
            CompleteDropdown(
                value = condition,
                onValueChange = {condition = it},
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