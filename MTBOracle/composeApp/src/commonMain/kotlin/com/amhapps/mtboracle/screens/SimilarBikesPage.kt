package com.amhapps.mtboracle.screens

import BikeData
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.amhapps.mtboracle.BikeInputDisplay
import com.amhapps.mtboracle.CompleteDropdown
import com.amhapps.mtboracle.EbayBikeData
import com.amhapps.mtboracle.EbayResponseException
import com.amhapps.mtboracle.EbaySearcher
import com.amhapps.mtboracle.LoadingAnimation
import com.amhapps.mtboracle.MTBOracleTheme
import com.amhapps.mtboracle.NoInternetException
import com.amhapps.mtboracle.SpecText
import com.amhapps.mtboracle.WarningDialog
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.launch
import mtboracle.composeapp.generated.resources.Res
import mtboracle.composeapp.generated.resources.transparent_mtb_oracle_bike_v2
import org.jetbrains.compose.resources.painterResource

abstract class SimilarBikesPage(private val navController:NavController,private val bikeData: BikeData) {
    protected var ebayBikes = listOf<EbayBikeData>()
    protected var moreBikes =  true
    abstract val ebaySearcher:EbaySearcher //can't use expect inside a class
    protected var bikesFound = false
    protected var retry = false
    protected var connectionError = 0
    protected var noMatchingBikes = false
    protected var sortBy = "Best Match"
    //Doesn't work - a mutable state is needed for a recomposition
    //A recomposition only occurs when an argument changes
    //Need to pass every one of these a lambda so that the value can be changes
    //Only do this for the necessary ones that are needed to cause a recomposition
    //However,this needs to work in valuation as well
    //Some variables can be self contained
    //However these must take place inside lazyColumn items as other sections of the LazyList are not Composable
    //eBayBikes must be mutable and affects both functions
    @Composable
    open fun show(){
        println("Composing")
        Box( //Needed for the home button
            modifier = Modifier
                .fillMaxSize()
        ) {
            RetrieveBikes()
            LazyColumn(
                userScrollEnabled = true,
                state = rememberLazyListState(),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                SimilarBikesBody(this@LazyColumn,true)
            }
            Column(
                modifier = Modifier.align(Alignment.BottomCenter)
            ){
                HomeButton()
            }
        }
    }

    @Composable
    fun RetrieveBikes(){
        if(!bikesFound) LoadingAnimation()
        FindBikes()
        ErrorDialogs()
    }

    //Being a receiver makes it hard to access from outside the class
    fun SimilarBikesBody(lazyListScope: LazyListScope,displayInputs: Boolean){
        lazyListScope.SimilarBikesHeader(displayInputs)
        lazyListScope.EbayBikeItems()
        //A spacer here breaks the infinite scrolling and causes infinite loading
        lazyListScope.RefreshBottom()
    }



    private fun encodeSortBy(sortBy:String):String{
        return when(sortBy){
            "Best Match" -> "bestMatch"
            "Price low to high" -> "price"
            "Price high to low" -> "-price"
            "Newest listings" -> "newlyListed"
            else -> "bestMatch"
        }
    }

    @Composable
    abstract fun HomeButton()

    @Composable
    fun ErrorDialogs(){
        if(noMatchingBikes){
            WarningDialog(
                confirmText = "Back",
                onConfirmation = { back() },
                dismissText = "Home",
                alwaysDismiss = {},
                explicitDismiss = { navController.popBackStack(platformHomeScreen(),false) },
                dialogTitle = "No Bikes Found",
                dialogText = "We could not find any bikes that matched the details you provided.\nChanging the model name to something broader may yield more results.",
                confirmationColor = MTBOracleTheme.colors.forestLight,
                dismissColor = Color.Gray
            )
        }
        if(connectionError > 0){
            WarningDialog(
                confirmText = "Retry",
                onConfirmation = {retry = !retry ; bikesFound = false}, //Value doesn't matter only matters that value changes
                dismissText = if(connectionError <= 2) "Home" else "Cancel",
                alwaysDismiss = {connectionError = 0},
                explicitDismiss = { if(connectionError <= 2) navController.popBackStack(platformHomeScreen(),false) },
                dialogTitle = "Network Error",
                dialogText = if(connectionError % 2 == 0) "No internet connection" else "Could not connect with eBay",
                confirmationColor = MTBOracleTheme.colors.forestLight,
                dismissColor = Color.Gray,
            )
        }
    }

    @Composable
    fun FindBikes(){
        val scope = rememberCoroutineScope()
        LaunchedEffect(retry) {//LaunchedEffect restarts when one of the key parameters changes
            scope.launch {
                if(ebayBikes.isEmpty() && !bikesFound){
                    try {
                        ebayBikes = ebaySearcher.search(bikeData,0,encodeSortBy(sortBy))
                        if(ebayBikes.isEmpty()) noMatchingBikes = true
                    }
                    catch (e: HttpRequestTimeoutException) {
                        println(e.toString())
                        connectionError = 1
                    }
                    catch(e: EbayResponseException){ //Kotlin doesn't have multi-catch
                        println(e.toString())
                        connectionError = 1
                    }
                    catch(e: NoInternetException){
                        println(e.toString())
                        connectionError = 2
                    }
                    finally{

                        bikesFound = true //stop the loading animation
                        println("bikesFoundFinally "+bikesFound)
                    }
                }

            }
        }
    }

    fun LazyListScope.SimilarBikesHeader(displayInputs:Boolean){
        item{
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    "Similar Bikes",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(0.dp, 10.dp)
                )
                if(displayInputs) BikeInputDisplay(bikeData)
                val options = listOf("Best Match","Price low to high","Price high to low","Newest listings")
                CompleteDropdown(value = sortBy, //Even if the user hasn't changed the sort, refresh to show some feedback
                    onDropdownClick = {
                        sortBy = it
                        retry = !retry
                        bikesFound = false
                        ebayBikes = emptyList()
                    },
                    label = "Sort By",
                    modifier = Modifier,
                    items=options
                )
            }

        }
    }


    fun LazyListScope.EbayBikeItems(){
        for (i in 0..<ebayBikes.size step 2) {
            item {
                Row {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(10.dp, 5.dp, 5.dp, 5.dp)
                    ) {
                        BikeCard(ebayBikes[i])
                    }
                    if (ebayBikes.size > (i + 1)) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp, 5.dp, 10.dp, 5.dp)
                        ) {
                            BikeCard(ebayBikes[i + 1])
                        }
                    }
                }
            } //once the first one is drawn, the second one only has 0.5 for its max width
        }
    }

    fun LazyListScope.RefreshBottom(){
        item {
            println("bottom")
            //When user scrolls to bottom of the page
            if (ebayBikes.isNotEmpty()) { //On the first composition, this would be on the page
                // as the bikes have not been returned from eBay yet and so it would run (which we don't want)
                LaunchedEffect(true) {
                    bikesFound = false
                    val prevSize = ebayBikes.size
                    if (prevSize % 48 == 0 && moreBikes) { //More bikes is the case that the number of bikes returned is a multiple of 48
                        try { //adding the lists changes the address of the list and so causes a recomposition
                            ebayBikes =
                                ebayBikes + ebaySearcher.search(bikeData, prevSize,encodeSortBy(sortBy))
                            if (ebayBikes.size == prevSize) moreBikes = false
                        } catch (e: HttpRequestTimeoutException) {
                            connectionError = 3
                        } catch (e: EbayResponseException) {
                            connectionError = 3
                        } catch (e: NoInternetException) {
                            connectionError = 4
                        }
                    }
                    println("Bikes found = true")
                    bikesFound =
                        true //Still want to stop checking if we are not a multiple of 48

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
    abstract fun platformHomeScreen():Any
    abstract fun back()
}
