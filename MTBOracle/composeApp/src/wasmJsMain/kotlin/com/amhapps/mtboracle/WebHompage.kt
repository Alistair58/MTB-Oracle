package com.amhapps.mtboracle

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.amhapps.mtboracle.screens.Homepage
import mtboracle.composeapp.generated.resources.Res
import mtboracle.composeapp.generated.resources.transparent_mtb_oracle_bike_v2
import org.jetbrains.compose.resources.painterResource

class WebHomepage(private var navController: NavController) : Homepage(navController){
    @Composable
    override fun cartoonBike(){
        Image(
            painter = painterResource(Res.drawable.transparent_mtb_oracle_bike_v2),
            contentDescription = "Logo",
            modifier = Modifier
                .height(300.dp) //Bigger than the mobile one - so that it overlaps properly
                .width(355.dp)
                .zIndex(3f) //Will be drawn on top of everything else
        )
    }
}