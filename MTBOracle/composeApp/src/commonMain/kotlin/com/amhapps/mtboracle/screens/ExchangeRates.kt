package com.amhapps.mtboracle.screens

import androidx.compose.ui.input.key.Key.Companion.Calendar
import com.amhapps.mtboracle.EbayResponseException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.serialization.json.Json

abstract class ExchangeRates {
    suspend fun get(currency:String):Float {
        val currDate = getCurrHmrcDate()
        var rate = getCachedRate(currency,currDate)
        if(rate < 0) rate = refreshRates(currency,currDate)
        return rate
    }

    private fun getCurrHmrcDate():String{
        val hmrcFormat = DateTimeComponents.Format {
            year()
            char('-')
            monthNumber(padding = Padding.NONE)
        }
        return Clock.System.now().format(hmrcFormat)
    }
    abstract suspend fun getCachedRate(currency:String,currDate:String):Float

    abstract suspend fun cacheRate(currency:String,rate:Float)
    abstract suspend fun storeDate(currDate:String)

    private suspend fun refreshRates(currency: String,currDate: String):Float{ //and return the requested currency while we're here
        val client = HttpClient() {
            install(HttpTimeout) {
                requestTimeoutMillis = 5000
                //throws a HttpRequestTimeoutException
            }
        }
        val requestBuilder = HttpRequestBuilder()

        requestBuilder.url {
            protocol = URLProtocol.HTTPS
            host = "trade-tariff.service.gov.uk"
            encodedPath = "/api/v2/exchange_rates/files/monthly_csv_${currDate}.csv"
        }
        val res = client.get(requestBuilder)
        if(res.status.value in 200..299){
            try{
                storeDate(currDate)
                val rows = res.bodyAsText().split("\n")
                for(i in rows.indices){
                    if(i==0) continue //first row is headings
                    val row = rows[i].split(",")
                    cacheRate(row[2],row[3].toFloat()) //CUR rate
                    if(row[2]==currency) return row[3].toFloat()
                }
            }
            catch(e:NumberFormatException){
                throw ExchangeRateResponseException(-1)
            }
        }
        else{
            throw ExchangeRateResponseException(res.status.value)
        }
        throw ExchangeRateResponseException(-1)
    }


}

class ExchangeRateResponseException(value:Int):Throwable()