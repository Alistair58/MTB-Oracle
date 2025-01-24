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

abstract class BrandModelYearForm(private val navController: NavHostController, private var bikeData:BikeData,private val isValuation:Boolean){
    @Composable
    abstract fun ShowForm() //TODO make this not abstract
        @Composable
    abstract fun NextButton(brand:String,model:String,year:String,models:List<String>)

}