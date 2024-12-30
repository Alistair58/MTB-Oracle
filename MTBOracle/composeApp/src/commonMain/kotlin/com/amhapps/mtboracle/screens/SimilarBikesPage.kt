package com.amhapps.mtboracle.screens

import BikeData
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.amhapps.mtboracle.EbaySearcher
import kotlinx.coroutines.launch

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
                var text by remember { mutableStateOf("Loading") }
                val scope = rememberCoroutineScope()
                val ebaySearcher by remember { mutableStateOf(EbaySearcher()) }
                LaunchedEffect(true) {
                    scope.launch {
                        text = try {
                            ebaySearcher.search(bikeData)
                        } catch (e: Exception) {
                            e.toString()
                        }
                    }
                }
                Text(
                    text = text
                )
            }
        }

    }

    @Composable
    fun getBikes(){

    }
}