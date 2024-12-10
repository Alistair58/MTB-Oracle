package com.amhapps.mtboracle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

class YearAndMaterialForm(private val navController: NavHostController,private val brand:String,private val model:String) {
    @Composable
    fun ShowForm(){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){
            var year by remember { mutableStateOf("") }
            MTBOracleTextInput(
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                value = year,
                onValueChange = { year = it },
                label = {
                    Text("Year:")
                    Text("Even if you don't know the exact year, putting in a rough year will give a more accurate valuation", fontSize = 12.sp)
                        },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp,20.dp)
            )

            val materials = listOf("Aluminium","Carbon fibre","Chromoly","Steel","Titanium","Other")
            var material by remember { mutableStateOf("") }
            var isDropDownExpanded by remember { mutableStateOf(false) }
            DropdownMenu(
                expanded = isDropDownExpanded,
                onDismissRequest = {
                    isDropDownExpanded = false
                },
                modifier = Modifier
                    .padding(0.dp,20.dp)
            ) {
                    materials.forEach { materialOption ->
                        DropdownMenuItem(onClick = {
                            material = materialOption
                            isDropDownExpanded = false
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

                ){
                Text(text = "Next", color = Color.White)
            }
        }
    }
}