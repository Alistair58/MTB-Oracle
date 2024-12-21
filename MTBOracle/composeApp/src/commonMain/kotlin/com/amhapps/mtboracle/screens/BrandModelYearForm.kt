package com.amhapps.mtboracle.screens

import BikeData
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
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
import com.amhapps.mtboracle.CategoryConditionCountryScreen
import com.amhapps.mtboracle.MTBOracleTextInput
import com.amhapps.mtboracle.MTBOracleTheme
import com.amhapps.mtboracle.WarningDialog

open class BrandModelYearForm(private val navController: NavHostController, private var bikeData:BikeData){
    @Composable
    open fun ShowForm() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()

        ) {
            Text(
                text = "Please enter the following information so" +
                        " that we can accurately estimate the value of your bike",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(40.dp, 5.dp)
            )
            var brand by remember { mutableStateOf(bikeData.brand) }
            MTBOracleTextInput(
                value = brand,
                onValueChange = { brand = it },
                label = { Text("Brand:") },
                modifier = Modifier
                    .padding(0.dp, 20.dp)
            )
            var model by remember { mutableStateOf(bikeData.model) }
            MTBOracleTextInput(
                value = model,
                onValueChange = { model = it },
                label = { Text("Model:") },
                modifier = Modifier
                    .padding(0.dp, 20.dp)
            )
            var year by remember { mutableStateOf(if (bikeData.year != -1) bikeData.year.toString() else "") }
            MTBOracleTextInput(
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                value = year,
                onValueChange = { year = it },
                label = {
                    Text("Year:")
                },
                modifier = Modifier
                    .padding(0.dp, 20.dp)
            )
            Text(
                "Even if you don't know the exact year, putting in a rough year will give a more accurate valuation",
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(40.dp, 5.dp)
            )
            NextButton(brand,model,year)
        }
    }
        @Composable
        open fun NextButton(brand:String,model:String,year:String){
            var numberErrorDialog by remember { mutableStateOf(false) }
            Button(
                onClick = {
                    try{
                        var yearInt:Int = -1
                        if(year != "") yearInt = year.toInt()
                        bikeData.brand = brand.trim()
                        bikeData.model = model.trim()
                        bikeData.year = yearInt
                        navController.navigate(
                            CategoryConditionCountryScreen(bikeData = bikeData),
                            navOptions =  navOptions {
                                restoreState = true
                            }
                        )
                    }
                    catch(e:NumberFormatException){
                        numberErrorDialog = true
                    }


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
            if(numberErrorDialog){
                WarningDialog(
                    onConfirmation = {},
                    dialogTitle = "Year must be a number",
                    dialogText = "",
                    confirmExists = false,
                    dismissColor = MTBOracleTheme.colors.forestLight,
                    onDismiss = {numberErrorDialog = false}
                )
            }
        }

}