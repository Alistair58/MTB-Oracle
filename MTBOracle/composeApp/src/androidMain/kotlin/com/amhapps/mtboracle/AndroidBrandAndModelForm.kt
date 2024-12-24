package com.amhapps.mtboracle

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.mutableStateListOf
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
import com.amhapps.mtboracle.screens.BrandModelYearForm

class AndroidBrandModelYearForm(private val navController: NavHostController, private var bikeData:AndroidBikeData, private val androidDataset: AndroidDataset):
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()

        ) {

            var brand by remember { mutableStateOf(bikeData.brand) }
            var models by remember { mutableStateOf(androidDataset.findModels(brand.trim())) }
            MTBOracleTextInput(
                value = brand,
                onValueChange = {
                    brand = it
                    models = androidDataset.findModels(it.trim())
                                },
                label = { Text("Brand:") },
                modifier = Modifier
                    .padding(0.dp, 20.dp)
            )
            var model by remember { mutableStateOf(bikeData.model) }

            SearchableDropdown(
                value = model,
                onValueChange = { model = it },
                onDropdownClick = { model = it },
                label = "Model:",
                modifier = Modifier
                    .padding(0.dp, 20.dp),
                items = models,
                iconContentDescription = "Model Dropdown"
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
            NextButton(brand,model,year,models)
        }


    }
    @Composable
    override fun NextButton(brand:String,model:String,year:String,models:List<String>){
        var numberErrorDialog by remember { mutableStateOf(false) }
        var lackOfInfo by remember { mutableStateOf(false) }
        var unknownModel by remember { mutableStateOf(false) }
        Button(
            onClick = {
                try{
                    var yearInt:Int = -1
                    if(year != "") yearInt = year.toInt()
                    bikeData.brand = brand.trim()
                    bikeData.model = model.trim()
                    bikeData.year = yearInt
                    if(bikeData.year ==-1 ||  bikeData.brand=="" || bikeData.model=="") lackOfInfo = true
                    if(!models.contains(bikeData.model)) unknownModel = true
                    if(!unknownModel && !lackOfInfo){
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
        if(lackOfInfo || unknownModel){
            var warningText= ""
            if(lackOfInfo) warningText+="• Empty fields will lead to a less accurate price"
            if(lackOfInfo && unknownModel) warningText+="\n\n"
            if(unknownModel) warningText+="• This brand and model combination is unknown to MTB Oracle." +
                    " Proceeding may lead to an inaccurate valuation"
            WarningDialog(
                onConfirmation = {
                    navController.navigate(
                        AndroidCategoryConditionCountryScreen(bikeData = bikeData),
                        navOptions =  navOptions {
                            restoreState = true
                        }
                ) },
                dialogTitle = "Warning",
                dialogText = warningText,
                confirmExists = true,
                confirmationColor = MTBOracleTheme.colors.yellowWarning,
                dismissColor = MTBOracleTheme.colors.forestLight,
                onDismiss = {
                    lackOfInfo = false
                    unknownModel = false
                }
            )
        }
    }

}