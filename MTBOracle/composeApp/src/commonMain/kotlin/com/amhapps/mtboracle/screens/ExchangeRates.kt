package com.amhapps.mtboracle.screens

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
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ExchangeRates {
    suspend fun get() {
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
            encodedPath = "/api/v2/exchange_rates/files/monthly_csv_2025-1.csv"
        }
        //TODO Parse CSV exchange rates - steal code from dataset?
        val res = client.get(requestBuilder)
        println(res.bodyAsText())
    }
}