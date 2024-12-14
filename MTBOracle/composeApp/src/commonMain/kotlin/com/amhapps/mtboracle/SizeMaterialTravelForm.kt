package com.amhapps.mtboracle

import Bike
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController

class SizeMaterialTravelForm(private val navController: NavHostController, private val bike:Bike) {
    @Composable
    fun ShowForm(){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ){

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