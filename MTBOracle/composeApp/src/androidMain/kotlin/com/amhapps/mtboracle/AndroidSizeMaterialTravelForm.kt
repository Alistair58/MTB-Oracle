package com.amhapps.mtboracle

import BikeData
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.amhapps.mtboracle.screens.SizeMaterialTravelForm
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AndroidSizeMaterialTravelForm(
    private val navController: NavHostController,
    private var bikeDataInput:AndroidBikeData,
    private val isValuation:Boolean,
    private val context: Context
) : SizeMaterialTravelForm(navController,bikeDataInput,isValuation) {
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
    override fun NextButton(size:String,wSize:String,fTravel:String,rTravel:String,material:String){
        val bikeData by remember { mutableStateOf(bikeDataInput) }
        var numberErrorDialog by remember { mutableStateOf(false) }
        var lackOfInfo by remember {mutableStateOf(false)}
        var nextScreen by remember { mutableStateOf(false) }
        if(nextScreen){
            NextScreen(bikeData)
            nextScreen = false //don't keep on loading it
        }
        Button(
            onClick = {
                try {
                    var fTravelInt:Float = -1f
                    var rTravelInt:Float = -1f
                    if(fTravel != "") fTravelInt = fTravel.toFloat()
                    if(rTravel != "") rTravelInt = rTravel.toFloat()
                    bikeData.size = size
                    bikeData.wheelSize = wSize
                    bikeData.frontTravel = fTravelInt
                    bikeData.rearTravel = rTravelInt
                    bikeData.material = material
                    if(bikeData.size=="" ||  bikeData.wheelSize=="" || bikeData.frontTravel==-1f
                        || bikeData.rearTravel == -1f || material == "") lackOfInfo = true
                    else{
                        nextScreen = true
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
                dialogTitle = "Suspension travel must be a number",
                dialogText = "",
                confirmExists = false,
                dismissColor = MTBOracleTheme.colors.forestLight,
                alwaysDismiss = {numberErrorDialog = false}
            )
        }
        if(lackOfInfo){
            WarningDialog(
                onConfirmation = { nextScreen = true },
                dialogTitle = "Warning",
                dialogText = "Empty fields will lead to a less accurate price",
                confirmExists = true,
                confirmationColor = MTBOracleTheme.colors.yellowWarning,
                dismissColor = MTBOracleTheme.colors.forestLight,
                alwaysDismiss = {lackOfInfo = false}
            )
        }
    }

    @Composable
    private fun NextScreen(bikeData: AndroidBikeData){
        //Can't cache here as the coroutine is destroyed when we navigate

        navController.navigate(
            if(isValuation)
                AndroidValuationScreen(bikeData)
            else
                AndroidSimilarBikesScreen(bikeData),
            navOptions =  navOptions {
                restoreState = true
            }
        )
    }




}