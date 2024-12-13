package com.amhapps.mtboracle

import Bike
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

class CategoryConditionCountryForm(private val navController: NavHostController,private val bike:Bike) {
    @Composable
    fun ShowForm(){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ){

            val materials = listOf("Aluminium","Carbon fibre","Chromoly","Steel","Titanium","Other")
            var material by remember { mutableStateOf("") }
            var dropDownExpanded by remember { mutableStateOf(false) }
            var dropDownSize by remember { mutableStateOf(Size.Zero)}
            val icon = if (dropDownExpanded)
                Icons.Filled.KeyboardArrowUp
            else
                Icons.Filled.KeyboardArrowDown
            MTBOracleTextInput(
                value = material,
                onValueChange = { material = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        // This value is used to assign to
                        // the DropDown the same width
                        dropDownSize = coordinates.size.toSize()
                    },
                label = {Text("Material")},
                readOnly = true,
                trailingIcon = {
                    Icon(icon,"Material Dropdown Arrow",
                        Modifier.clickable {dropDownExpanded = !dropDownExpanded })
                }
            )
            DropdownMenu(
                expanded = dropDownExpanded,
                onDismissRequest = {
                    dropDownExpanded = false
                },
                modifier = Modifier
                    .padding(0.dp,20.dp)
                    .width(with(LocalDensity.current){dropDownSize.width.toDp()})
                    //set it to same width as input box
            ) {
                    materials.forEach { materialOption ->
                        DropdownMenuItem(onClick = {
                            material = materialOption
                            dropDownExpanded = false
                        }) {
                            Text(text = materialOption)
                        }
                    }
            }
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