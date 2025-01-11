package com.amhapps.mtboracle

import BikeData
import androidx.activity.compose.BackHandler
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
import com.amhapps.mtboracle.screens.CategoryConditionCountryForm

class AndroidCategoryCountryConditionForm(private val navController: NavHostController, private var bikeDataInput:AndroidBikeData,private val isValuation:Boolean) : CategoryConditionCountryForm(navController,bikeDataInput,isValuation) {
    @Composable
    override fun ShowForm(){
        BackHandler {
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("bikeData",bikeDataInput)
            navController.popBackStack()
        }
        super.ShowForm()
    }

    @Composable
    override fun NextButton(category:String,condition:String,country:String) {
        var lackOfInfo by remember { mutableStateOf(false) }
        val bikeData by remember { mutableStateOf(bikeDataInput) }
        Button(
            onClick = {
                bikeData.category = category.trim()
                bikeData.condition = condition.trim()
                bikeData.country = country.trim()
                if(bikeData.category=="" ||  bikeData.condition=="" || bikeData.country=="") lackOfInfo = true
                else{
                    navController.navigate(
                        AndroidSizeMaterialTravelScreen(bikeData,isValuation),
                        navOptions =  navOptions {
                            restoreState = true
                        }
                    )
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
        if(lackOfInfo){
            WarningDialog(
                onConfirmation = {
                    navController.navigate(
                        AndroidSizeMaterialTravelScreen(bikeData,isValuation),
                        navOptions =  navOptions {
                            restoreState = true
                        }
                ) },
                dialogTitle = "Warning",
                dialogText = "Empty fields will lead to a less accurate price",
                confirmExists = true,
                confirmationColor = MTBOracleTheme.colors.yellowWarning,
                dismissColor = MTBOracleTheme.colors.forestLight,
                alwaysDismiss = {lackOfInfo = false}
            )
        }
    }

}