package com.amhapps.mtboracle.screens

import BikeData
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
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
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.amhapps.mtboracle.EbayBikeData
import com.amhapps.mtboracle.EbaySearcher
import com.amhapps.mtboracle.LoadingAnimation
import com.amhapps.mtboracle.MTBOracleTheme
import com.amhapps.mtboracle.SpecText
import kotlinx.coroutines.delay
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
            var ebayBikes by remember { mutableStateOf(listOf<EbayBikeData>()) }
            var errorText by remember { mutableStateOf("") }
            var moreBikes by remember{ mutableStateOf(true) }
            val scope = rememberCoroutineScope()
            val ebaySearcher by remember { mutableStateOf(EbaySearcher()) }
            var bikesFound by remember{ mutableStateOf(false) }
            if(!bikesFound) LoadingAnimation()
            LaunchedEffect(!bikesFound) {
                scope.launch {
                    if(ebayBikes.isEmpty()){
                        try {
                            ebayBikes = ebaySearcher.search(bikeData,0)
                            println(ebayBikes.toString())
                            bikesFound = true

                        } catch (e: Exception) {
                            errorText = e.toString()
                        }
                    }

                }
            }
            LazyColumn(
                userScrollEnabled = true,
                state = rememberLazyListState()
            )
             {

                item{Text(
                    text = errorText,
                    color = MTBOracleTheme.colors.lightRed
                )}
                println("Loop "+ ebayBikes.size)
                for(i in 0..<ebayBikes.size step 2){
                    item{Row {
                        Column (modifier =Modifier
                            .fillMaxWidth(0.5f)
                            .padding(10.dp,5.dp,5.dp,5.dp)
                        ) {
                            BikeCard(ebayBikes[i])
                        }
                        if(ebayBikes.size>(i+1)){
                            Column (modifier =Modifier
                                .fillMaxWidth()
                                .padding(5.dp,5.dp,10.dp,5.dp)
                            ) {
                                BikeCard(ebayBikes[i+1])
                            }
                        }
                    }} //once the first one is drawn, the second one only has 0.5 for its max width
                }

             item {
                 //When user scrolls to bottom of the page
                 LaunchedEffect(true) {
                     bikesFound = false
                     val prevSize = ebayBikes.size
                     if(prevSize %48==0 && moreBikes){
                         try { //adding the lists changes the address of the list and so causes a recomposition
                             ebayBikes = ebayBikes + ebaySearcher.search(bikeData,prevSize)
                             bikesFound = true
                             println(ebayBikes.toString())
                             if(ebayBikes.size == prevSize) moreBikes = false
                             println(ebayBikes.size)
                         } catch (e: Exception) {
                             errorText = e.toString()
                         }
                     }

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
                .height(300.dp)
        ) {
            println(bikeData.imageUrl)
            Column (
                modifier = Modifier.padding(10.dp)
            ){
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(bikeData.imageUrl)
                        .crossfade(true)
                        .build(),
                    loading = {
                        CircularProgressIndicator()
                    },
                    error = {painterResource(Res.drawable.transparent_mtb_oracle_bike_v2)},
                    onError = { println(it.result.toString()) },
                    contentDescription = bikeData.title+" image",
                    modifier = Modifier
                        .height(80.dp)
                )
                Text(
                    bikeData.title ?: "Similar Bike",
                    fontWeight = FontWeight.Bold,
                    fontSize =
                    (if(bikeData.title == null) 0.sp
                        else if(bikeData.title.length<=20) 16.sp
                        else if(bikeData.title.length <=60) 14.sp
                        else 12.sp) //max eBay title length is 80 characters
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