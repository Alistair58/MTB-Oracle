package com.amhapps.mtboracle

import BikeData
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.amhapps.mtboracle.screens.BrandModelYearForm

class AndroidBrandModelYearForm(private val navController: NavHostController, private var bikeData:AndroidBikeData):
    BrandModelYearForm(navController,bikeData){
    @Composable
    override fun ShowForm(){
        var showAlert by remember { mutableStateOf(false) }
        if(showAlert){
            WarningDialog(
                onConfirmation = { navController.popBackStack() },
                dialogTitle = "Cancel this bike?",
                dialogText = "",
                confirmationColor = MTBOracleTheme.colors.lightRed,
                dismissColor = MTBOracleTheme.colors.forestLight,
                onDismiss = {showAlert = false}

            )
        }
        BackHandler {
            showAlert = !showAlert
        }
        super.ShowForm()
    }
    @Composable
    override fun NextButton(brand:String,model:String,year:String){
        var numberErrorDialog by remember { mutableStateOf(false) }
        var lackOfInfo by remember { mutableStateOf(false) }
        Button(
            onClick = {
                try{
                    var yearInt:Int = -1
                    if(year != "") yearInt = year.toInt()
                    bikeData.brand = brand.trim()
                    bikeData.model = model.trim()
                    bikeData.year = yearInt
                    if(bikeData.year ==-1 ||  bikeData.brand=="" || bikeData.model=="") lackOfInfo = true
                    else{
                        navController.navigate(
                            AndroidCategoryConditionCountryScreen(bikeData = bikeData),
                            navOptions =  navOptions {
                                restoreState = true
                            }
                        )
                    }

                }
                catch(e:NumberFormatException){
                    numberErrorDialog = true
                }


            },
            colors = MTBOracleTheme.buttonColors,
            modifier = Modifier
                .padding(0.dp, 30.dp)
                .height(50.dp)
                .width(100.dp)
        ){
            Text(text = "Next",
                color = Color.White,
                fontSize = 20.sp)
        }
        if(numberErrorDialog){
            WarningDialog(
                onConfirmation = {  },
                dialogTitle = "Year must be a number",
                dialogText = "",
                confirmExists = false,
                dismissColor = MTBOracleTheme.colors.forestLight,
                onDismiss = {numberErrorDialog = false}
            )
        }
        if(lackOfInfo){
            WarningDialog(
                onConfirmation = {
                    navController.navigate(
                        AndroidCategoryConditionCountryScreen(bikeData = bikeData),
                        navOptions =  navOptions {
                            restoreState = true
                        }
                ) },
                dialogTitle = "Warning",
                dialogText = "Empty fields will lead to a less accurate price",
                confirmExists = true,
                confirmationColor = MTBOracleTheme.colors.yellowWarning,
                dismissColor = MTBOracleTheme.colors.forestLight,
                onDismiss = {lackOfInfo = false}
            )
        }
    }

}