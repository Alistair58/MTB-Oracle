package com.amhapps.mtboracle.screens

import BikeData
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

abstract class ValuationPage(protected val navController: NavHostController, private val bikeData: BikeData){
    @Composable
    open fun show(){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            val output = "Your bike is worth £" + valuation()
            Text(text = output,
                fontSize = 30.sp,
                modifier = Modifier
                    .padding(30.dp,30.dp)
            )
        }
    }
    abstract fun valuation():Float
}