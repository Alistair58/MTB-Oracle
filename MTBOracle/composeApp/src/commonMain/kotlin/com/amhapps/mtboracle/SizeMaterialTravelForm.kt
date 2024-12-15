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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
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

class SizeMaterialTravelForm(private val navController: NavHostController, private val bikeData: BikeData) {
    @Composable
    fun ShowForm(){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ){
            val sizes = listOf("XS","S","M","L","XL","XXL")
            var size by remember { mutableStateOf("") }
            CompleteDropdown(size,
                onValueChange = { size = it },
                onDropdownClick = { size = it },
                iconContentDescription = "Sizes Dropdown",
                items = sizes,
                label="Size")

            val wSizes = listOf("<16\"","20\"","24\"","26\"","27.5\"/650B","29\"","650C","700C")
            var wSize by remember { mutableStateOf("") }
            CompleteDropdown(wSize,
                onValueChange = { wSize = it },
                onDropdownClick = { wSize = it },
                iconContentDescription = "Wheel Sizes Dropdown",
                items = wSizes,
                label="Wheel Size")

            var fTravel by remember { mutableStateOf("") }
            MTBOracleTextInput(
                value = fTravel,
                onValueChange = { fTravel = it },
                label = { Text("Front Suspension Travel:") },
                modifier = Modifier
                    .padding(0.dp,20.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            var rTravel by remember { mutableStateOf("") }
            MTBOracleTextInput(
                value = rTravel,
                onValueChange = { rTravel = it },
                label = { Text("Rear Suspension Travel:") },
                modifier = Modifier
                    .padding(0.dp,20.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            val materials = listOf("Aluminium","Carbon fibre","Chromoly","Steel","Titanium","Other")
            var material by remember { mutableStateOf("") }
            CompleteDropdown(material,
                onValueChange = { material = it },
                onDropdownClick = { material = it },
                iconContentDescription = "Materials Dropdown",
                items = materials,
                label="Material")
            Button(
                onClick = {
                    //navController.navigate(YearAndMaterialScreen(brand,model))
                },
                colors = MTBOracleTheme.buttonColors,
                modifier = Modifier
                    .padding(0.dp,20.dp)
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