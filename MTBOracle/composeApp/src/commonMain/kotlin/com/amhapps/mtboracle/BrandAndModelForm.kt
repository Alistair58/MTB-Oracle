package com.amhapps.mtboracle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

class BrandAndModelForm(private val navController: NavHostController) {
    @Composable
    fun ShowForm(){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()

        ){
            Text(
                text = "Please enter the following information so" +
                        " that we can accurately estimate the value of your bike" ,
                fontSize = 14.sp,
                color = Color.Black
            )
            var brand by remember { mutableStateOf("") }
            MTBOracleTextInput(
                value = brand,
                onValueChange = { brand = it },
                label = { Text("Brand:") },
                modifier = Modifier
                    .padding(0.dp,20.dp)
            )
            var model by remember { mutableStateOf("") }
            MTBOracleTextInput(
                value = model,
                onValueChange = { model = it },
                label = { Text("Model:") },
                modifier = Modifier
                    .padding(0.dp,20.dp)
            )
            Button(
                onClick = {
                    navController.navigate(YearAndMaterialScreen(brand,model))
                },
                colors = MTBOracleTheme.buttonColors,
                modifier = Modifier
                    .padding(0.dp,20.dp)
                    .height(50.dp)
                    .width(100.dp)
                ){
                Text(text = "Next", color = Color.White)
            }
        }
    }
}