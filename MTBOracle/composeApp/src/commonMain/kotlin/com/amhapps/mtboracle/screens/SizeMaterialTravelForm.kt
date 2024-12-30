package com.amhapps.mtboracle.screens

import BikeData
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.amhapps.mtboracle.CompleteDropdown
import com.amhapps.mtboracle.MTBOracleTextInput
import com.amhapps.mtboracle.MTBOracleTheme
import com.amhapps.mtboracle.ValuationScreen

abstract class SizeMaterialTravelForm(private val navController: NavHostController, private val bikeData: BikeData,private val valuation:Boolean){
    @Composable
    open fun ShowForm(){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ){
            val sizes = listOf("XS","S","M","L","XL","XXL")
            var size by remember { mutableStateOf(bikeData.size) }
            CompleteDropdown(
                size,
                onValueChange = { size = it },
                onDropdownClick = { size = it },
                iconContentDescription = "Sizes Dropdown",
                items = sizes,
                label="Size",
                modifier = Modifier.padding(0.dp,20.dp)
            )

            val wSizes = listOf("<16\"","20\"","24\"","26\"","27.5\"/650B","29\"","650C","700C")
            var wSize by remember { mutableStateOf(bikeData.wheelSize) }
            CompleteDropdown(wSize,
                onValueChange = { wSize = it },
                onDropdownClick = { wSize = it },
                iconContentDescription = "Wheel Sizes Dropdown",
                items = wSizes,
                label="Wheel Size",
                modifier = Modifier.padding(0.dp,20.dp)
            )

            var fTravel by remember { mutableStateOf(if (bikeData.frontTravel !=-1f) bikeData.frontTravel.toString() else "") }
            MTBOracleTextInput(
                value = fTravel,
                onValueChange = { fTravel = it },
                label = { Text("Front Suspension Travel (mm):") },
                modifier = Modifier
                    .padding(0.dp,20.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            var rTravel by remember { mutableStateOf(if (bikeData.rearTravel !=-1f) bikeData.rearTravel.toString() else "" ) }
            MTBOracleTextInput(
                value = rTravel,
                onValueChange = { rTravel = it },
                label = { Text("Rear Suspension Travel (mm):") },
                modifier = Modifier
                    .padding(0.dp,20.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            val materials = listOf("Aluminium","Carbon fibre","Chromoly","Steel","Titanium","Other")
            var material by remember { mutableStateOf(bikeData.material) }
            CompleteDropdown(material,
                onValueChange = { material = it },
                onDropdownClick = { material = it },
                iconContentDescription = "Materials Dropdown",
                items = materials,
                label="Material",
                modifier = Modifier.padding(0.dp,20.dp))
            NextButton(size,wSize,fTravel,rTravel,material)
        }
    }

    @Composable
    abstract fun NextButton(size:String,wSize:String,fTravel:String,rTravel:String,material:String)

}