package com.amhapps.mtboracle.screens

import BikeData
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.amhapps.mtboracle.EbayBikeData
import com.amhapps.mtboracle.EbaySearcher
import com.amhapps.mtboracle.MTBOracleTheme
import com.amhapps.mtboracle.SpecText
import kotlinx.coroutines.launch
import mtboracle.composeapp.generated.resources.Res
import mtboracle.composeapp.generated.resources.transparent_mtb_oracle_bike_v2
import org.jetbrains.compose.resources.painterResource

open class SimilarBikesPage(private val bikeData: BikeData) {
    @Composable
    open fun show(){
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                var ebayBikes by remember { mutableStateOf(emptyList<EbayBikeData>()) }
                var errorText by remember { mutableStateOf("") }
                val scope = rememberCoroutineScope()
                val ebaySearcher by remember { mutableStateOf(EbaySearcher()) }
                LaunchedEffect(true) {
                    scope.launch {
                         try {
                            ebayBikes = ebaySearcher.search(bikeData)
                             println(ebayBikes.toString())
                        } catch (e: Exception) {
                            errorText = e.toString()
                        }
                    }
                }
                Text(
                    text = errorText,
                    color = MTBOracleTheme.colors.lightRed
                )
                Column {
                    Row {
                        if(ebayBikes.size>0) Column (modifier =Modifier.fillMaxWidth(0.5f)) { BikeCard(ebayBikes[0])}
                        if(ebayBikes.size>1) Column (modifier =Modifier.fillMaxWidth()) {BikeCard(ebayBikes[1])}
                    } //once the first one is drawn, the second one only has 0.5 for its max width
                    Row {
                        if(ebayBikes.size>2) Column (modifier =Modifier.fillMaxWidth(0.5f)) {BikeCard(ebayBikes[2])}
                        if(ebayBikes.size>3) Column (modifier =Modifier.fillMaxWidth()) {BikeCard(ebayBikes[3])}
                    }
                }

            }
        }

    }

    @Composable
    fun BikeCard(bikeData:EbayBikeData){
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            println(bikeData.imageUrl)
            Column (
                modifier = Modifier.padding(10.dp)
            ){
                AsyncImage(
                    model = bikeData.imageUrl,
                    contentDescription = bikeData.title + " image",
                    placeholder = painterResource(Res.drawable.transparent_mtb_oracle_bike_v2),
                    error = painterResource(Res.drawable.transparent_mtb_oracle_bike_v2),
                    onError = { println(it.result.toString()) },
                )
                Text(
                    bikeData.title ?: "Similar Bike",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(bikeData.price ?: "", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(0.dp,5.dp))
                SpecText("Condition: ", bikeData.condition ?: "Unknown", fontSize = 14.sp)
                SpecText(
                    "Location: ",
                    (bikeData.city
                        ?: "") + (if (bikeData.city != null && bikeData.country != null) ", " else "") + (bikeData.country
                        ?: ""),
                    fontSize = 14.sp
                )
                val annotatedLinkString: AnnotatedString = remember {
                    buildAnnotatedString {
                        val linkStyle = SpanStyle(
                            color = Color(0xff88b89b),//Wouldn't let me use the MTBOracle colours as they are Composable
                            textDecoration = TextDecoration.Underline
                        )

                        withLink(LinkAnnotation.Url(url = bikeData.itemWebUrl ?: "")) {
                            withStyle(
                                style = linkStyle
                            ) {
                                append("Link")
                            }
                        }
                    }
                }
                Text(annotatedLinkString)
            }

        }
    }
}