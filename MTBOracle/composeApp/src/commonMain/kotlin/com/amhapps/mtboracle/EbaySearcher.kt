package com.amhapps.mtboracle

import Bike
import BikeData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import io.ktor.http.headers
import kotlinx.serialization.Serializable
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.SerialName


class EbaySearcher {
    suspend fun search(bikeData:BikeData):String{ //:List<BikeData>
        val client = HttpClient(){
            install(ContentNegotiation){
                json()
            }
        }
        val accessBuilder = HttpRequestBuilder()
        accessBuilder.url {
            protocol = URLProtocol.HTTPS
            host = "api.ebay.com"
            encodedPath = "/identity/v1/oauth2/token"
        }
        accessBuilder.headers {
            append(HttpHeaders.ContentType,"application/x-www-form-urlencoded")
            append(HttpHeaders.Authorization,"Basic REMOVED")
        }
        accessBuilder.setBody("grant_type=client_credentials&scope=https%3A%2F%2Fapi.ebay.com%2Foauth%2Fapi_scope")
        val accessResponse = client.post(accessBuilder)
        if(accessResponse.status.value in 200..299){
            val accessResult:AccessResponse = accessResponse.body()
            val bikeReqBuilder = HttpRequestBuilder()
            bikeReqBuilder.url {
                protocol = URLProtocol.HTTPS
                host = "api.ebay.com" //
                encodedPath = "/buy/browse/v1/item_summary/search?q="+bikeData.year+"%20"+bikeData.brand+"%20"+bikeData.model+"&limit=4"
            }
            bikeReqBuilder.headers {
                append(HttpHeaders.Authorization,"Bearer "+accessResult.accessToken)
            }
            println(accessResult.accessToken)
            val response = client.get(bikeReqBuilder)
            return response.body()

        }
        else{
            throw Exception(accessResponse.status.value.toString()+" error received from eBay")
        }


    }
}

@Serializable
data class AccessResponse(
    @SerialName("access_token")
    val accessToken:String,
    @SerialName("expires_in")
    val expiresIn:Int,
    @SerialName("token_type")
    val tokenType:String
    )