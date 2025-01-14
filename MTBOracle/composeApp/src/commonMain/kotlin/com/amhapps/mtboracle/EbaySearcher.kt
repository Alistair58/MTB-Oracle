package com.amhapps.mtboracle

import Bike
import BikeData
import androidx.compose.runtime.Immutable
import coil3.network.NetworkClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
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
import io.ktor.http.encodeURLPath
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.delay
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import mtboracle.composeapp.generated.resources.Res
import kotlin.experimental.ExperimentalObjCRefinement


abstract class EbaySearcher {
    open suspend fun search(bikeData: BikeData, offset: Int? = 0,sortBy:String = "bestMatch"): List<EbayBikeData> {
        val client = HttpClient() {
            install(HttpTimeout){
                requestTimeoutMillis = 5000
                //throws a HttpRequestTimeoutException
            }
            install(ContentNegotiation) {
                json(Json {
                    this.ignoreUnknownKeys = true
                })
            }
        }
        println("Getting token")
        var accessToken:String = getCachedToken()
        println("Got token")
        if(accessToken.isEmpty()){
            val accessResponse = getAccessToken(client)
            if (accessResponse.status.value in 200..299) {
                val accessResponseBody: AccessResponse = accessResponse.body()
                accessToken = accessResponseBody.accessToken
                cacheToken(accessToken,accessResponseBody.expiresIn)
            }
            else {
                throw EbayResponseException(accessResponse.status.value)
            }
        }
        println(sortBy)
        val response =
            searchBikes(client,accessToken, bikeData, offset ?: 0,sortBy)
        println("Got response")
        println(response)
        val bikes = mutableListOf<EbayBikeData>()
        if (response.status.value in 200..299) {
            val bikeResponse: BikeResponse = response.body()
            val ebayBikes = bikeResponse.itemSummaries ?: emptyList()

            for (ebayBike in ebayBikes) {
                bikes.add(
                    EbayBikeData(
                        title = ebayBike.title,
                        price = ebayBike.price?.value + " " + ebayBike.price?.currency,
                        condition = ebayBike.condition,
                        imageUrl = ebayBike.image?.imageUrl,
                        itemWebUrl = ebayBike.itemWebUrl,
                        city = ebayBike.itemLocation?.city,
                        country = ebayBike.itemLocation?.country
                    )
                )
            }
            println("Response " + bikeResponse.toString())
            println("Ebay bikes " + ebayBikes.toString())
        }
        else {
            throw EbayResponseException(response.status.value)
        }

        return bikes.toList()

    }


    suspend fun getAccessToken(client: HttpClient): HttpResponse {
        val accessBuilder = HttpRequestBuilder()
        accessBuilder.url {
            protocol = URLProtocol.HTTPS
            host = "api.ebay.com"
            encodedPath = "/identity/v1/oauth2/token"
        }
        accessBuilder.headers {
            append(HttpHeaders.ContentType, "application/x-www-form-urlencoded")
            append(
                HttpHeaders.Authorization,
                "Basic REMOVED"
            )
        }
        accessBuilder.setBody("grant_type=client_credentials&scope=https%3A%2F%2Fapi.ebay.com%2Foauth%2Fapi_scope")
        return client.post(accessBuilder)
    }

    suspend fun searchBikes(
        client: HttpClient,
        accessToken: String,
        bikeData: BikeData,
        offset: Int,
        sortBy: String
    ): HttpResponse {
        println("Searching bikes")
        val bikeReqBuilder = HttpRequestBuilder()
        val encodedCondition = when (bikeData.condition) { //most bikes seem to just say used
            "Brand new" -> "1000|1500" //new|new other (see details)
            "Excellent" -> "2750|3000|4000" //like new|used|very good
            "Good" -> "3000|5000|6000" //used|good|acceptable
            "Poor" -> "3000|6000" //used|good|acceptable
            "For parts/unrideable" -> "3000|7000" //used|for parts or not working
            "Other" -> "1000|1500|2750|3000|4000|5000|6000|7000" //All of them
            else -> "3000"
        }
        val encodedSize = when (bikeData.size) {
            "S" -> "Small"
            "M" -> "Medium"
            "L" -> "Large"
            "XL" -> "X-Large"
            else -> ""
        }
        val encodedWheelSize = bikeData.wheelSize.replace("<", "")
            .split("/")[0]
            .replace("\"", "%20in")
        val encodedCategory =
            if (bikeData.category == "Children's") "Kids%20Bike"
            else "Mountain%20Bike"
        val encodedMaterial =
            if (bikeData.material == "Carbon fibre") "Carbon%20Fibre"
            else if (bikeData.material == "Other") "Not%20Specified"
            else bikeData.material
        val encodedSuspension =
            if (bikeData.rearTravel == 0f && bikeData.frontTravel == 0f) "No%20Suspension"
            else if (bikeData.frontTravel > 0f) {
                if (bikeData.rearTravel > 0f) "Full%20Suspension%20%28Front%20%26%20Rear%29"
                else "Front"
            } else ""
        println(bikeData.country)
        val countryId:String = ebayCountryIdMap[bikeData.country]?:"EBAY_US"

        var searchTerm =
            (if (bikeData.year < 0 || bikeData.year > 2050) "" else bikeData.year.toString() + "%20")
        if (bikeData.brand.length > 0) searchTerm += bikeData.brand.encodeURLPath() + "%20"
        if (bikeData.model.length > 0) searchTerm += bikeData.model.encodeURLPath()
        bikeReqBuilder.url {
            protocol = URLProtocol.HTTPS
            host = "api.ebay.com"
            encodedPath = "/buy/browse/v1/item_summary/search?q=" +
                    searchTerm +
                    "&category_ids=177831&limit=48" +// 177831 = Bicycles
                    "&offset=" + offset.toString() +
                    "&filter=buyingOptions:{FIXED_PRICE}" +
                    "&aspect_filter=categoryId:177831," +
                    "Frame%20Size:{" + encodedSize + "}," +
                    "Wheel%20Size:{" + encodedWheelSize + "}," +
                    "Bike%20Type:{" + encodedCategory + "}," +
                    "Suspension%20Type:{" + encodedSuspension + "}," +
                    "Material:{" + encodedMaterial + "}" +
                    "&filter=conditionIds:{" + encodedCondition + "}"+
                    "&sort="+sortBy


        }
        bikeReqBuilder.headers {
            append(HttpHeaders.Authorization, "Bearer " + accessToken)
            append("X-EBAY-C-MARKETPLACE-ID",
                countryId)
        }
        return client.get(bikeReqBuilder)
    }

    abstract suspend fun getCachedToken():String
    abstract suspend fun cacheToken(token:String,lifespanSeconds:Long)
}

class NoInternetException(): Throwable()
class EbayResponseException(val errorCode:Int? = -1): Throwable()

@Serializable
data class AccessResponse(
    @SerialName("access_token")
    val accessToken:String,
    @SerialName("expires_in")
    val expiresIn:Long,
    @SerialName("token_type")
    val tokenType:String
)

@Serializable
data class BikeResponse(
    val autoCorrections:AutoCorrections? = null,
    val href:String? = null,
    val itemSummaries:List<EbayBikeItem>? = null,
    val limit:Int? = null,
    val next:String? = null,
    val offset:Int? = null,
    val prev:String? = null,
    val refinement:Refinement? = null,
    val total:Int? = null,
    val warning:List<Warning>? = null,
)

val ebayCountryIdMap = hashMapOf( //Tested all on 13/1/25
    "Australia" to "EBAY_AU",
    "Austria" to "EBAY_AT",
    "Belgium" to "EBAY_BE",
    "Canada" to "EBAY_CA",
    "Switzerland" to "EBAY_CH",
    "Germany" to "EBAY_DE",
    "Spain" to "EBAY_ES",
    "France" to "EBAY_FR",
    "United Kingdom UK" to "EBAY_GB",
    "Hong Kong" to "EBAY_HK", //Not in my country list yet
    "Ireland" to "EBAY_IE",
    "Italy" to "EBAY_IT",
    //"Malaysia" to "EBAY_MY", Not working but Malaysian eBay website does exist
    "Netherlands" to "EBAY_NL",
    //"Philippines" to "EBAY_PH", Not working but eBay website does exist
    "Poland" to "EBAY_PL",
    "Singapore" to "EBAY_SG",
    //"Thailand" to "EBAY_TH",
    //"Taiwan" to "EBAY_TW",
    "United States of America USA" to "EBAY_US",
    //"Vietnam" to "EBAY_VN", //Not working but docs say it should https://developer.ebay.com/api-docs/commerce/identity/types/bas:MarketplaceIdEnum
)

@Serializable
data class EbayBikeData(
    val title: String?,
    val price:String?,
    val condition:String?,
    val imageUrl: String?,
    val itemWebUrl:String?,
    val city:String?,
    val country: String?
)

@Serializable
data class AutoCorrections(val q:String)

@Serializable
data class EbayBikeItem(
    val additionalImages:List<EbayImage>? = null,
    val adultOnly:Boolean? = null,
    val availableCoupons :Boolean? = null,
    val bidCount:Int? = null,
    val buyingOptions:List<String>? = null,
    val categories:List<EbayCategory>? = null,
    val compatibilityMatch:String? = null,
    val compatibilityProperties:List<CompatibilityProperty>? = null,
    val condition:String? = null,
    val conditionId:String? = null,
    val currentBidPrice:Price? = null,
    val distanceFromPickupLocation:DistanceFromPickupLocation? = null,
    val energyEfficiencyClass:String? = null,
    val epid:String? = null,
    val image:EbayImage? = null,
    val itemAffiliateWebUrl:String? = null,
    val itemCreationDate:String? = null,
    val itemEndDate:String? = null,
    val itemGroupHref:String? = null,
    val itemGroupType:String? = null,
    val itemHref:String? = null,
    val itemId:String? = null,
    val itemLocation:ItemLocation? = null,
    val itemWebUrl:String? = null,
    val leafCategoryIds:List<String>? = null,
    val legacyItemId:String? = null,
    val listingMarketplaceId:String? = null,
    val marketingPrice:MarketingPrice? = null,
    val pickupOptions:List<PickupOptions>? = null,
    val price:Price? = null,
    val priceDisplayCondition:String? = null,
    val priorityListing:Boolean,
    val qualifiedPrograms:List<String>? = null,
    val seller:Seller? = null,
    val shippingOptions:List<ShippingOption>? = null,
    val shortDescription:String? = null,
    val thumbnailImages:List<EbayImage>? = null,
    val title:String? = null,
    val topRatedBuyingExperience:Boolean? = null,
    val tyreLabelImageUrl:String? = null,
    val unitPrice:Price? = null,
    val unitPricingMeasure:String? = null,
    val watchCount:Int? = null


)
@Serializable
data class EbayImage(
    val height:Int? = null,
    val imageUrl:String? = null,
    val width:Int? = null,
)
@Serializable
data class EbayCategory(
    val categoryId:String? = null,
    val categoryName:String? = null,
)
@Serializable
data class CompatibilityProperty(
    val localizedName:String? = null,
    val name:String? = null,
    val value:String? = null
)
@Serializable
data class Price(
    val convertedFromCurrency:String? = null,
    val convertedFromValue:String? = null,
    val currency:String? = null,
    val value:String? = null
)
@Serializable
data class DistanceFromPickupLocation(
    val unitOfMeasure:String? = null,
    val value:String? = null,
)
@Serializable
data class ItemLocation(
    val addressLine1:String? = null,
    val addressLine2:String? = null,
    val city:String? = null,
    val country:String? = null,
    val county:String? = null,
    val postalCode:String? = null,
    val stateOrProvince:String? = null
)

@Serializable
data class MarketingPrice(
    val discountAmount:Price? = null,
    val discountPercentage:String? = null,
    val originalPrice:Price? = null,
    val priceTreatment:String? = null
)
@Serializable
data class PickupOptions(
    val pickupLocationType:String? = null
)

@Serializable
data class Seller(
    val feedbackPercentage:String? = null,
    val feedbackScore:Int? = null,
    val sellerAccountType:String? = null,
    val username:String? = null
)

@Serializable
data class ShippingOption(
    val guaranteedDelivery:Boolean? = null,
    val maxEstimatedDeliveryDate:String? = null,
    val minEstimatedDeliveryDate:String? = null,
    val shippingCost:Price? = null,
    val shippingCostType:String? = null
)

@Serializable
data class Refinement(
    val aspectDistributions: AspectDistributions? = null,
    val buyingOptionDistributions: List<BuyingOptionDistribution>? = null,
    val categoryDistributions: List<CategoryDistribution>? = null,
    val conditionDistributions: List<ConditionDistribution>? = null,
    val dominantCategoryId:String? = null

)

@Serializable
data class AspectDistributions(
    val aspectValueDistributions: List<AspectValueDistribution>? = null,
    val localizedAspectName:String? = null
)

@Serializable
data class AspectValueDistribution(
    val localizedAspectValue:String? = null,
    val matchCount:Int? = null,
    val refinementHref: String? = null
)

@Serializable
data class BuyingOptionDistribution(
    val buyingOption:String? = null,
    val matchCount:Int? = null,
    val refinementHref: String? = null
)

@Serializable
data class CategoryDistribution(
    val categoryId:String? = null,
    val categoryName:String? = null,
    val matchCount:Int? = null,
    val refinementHref: String? = null
)

@Serializable
data class ConditionDistribution(
    val condition:String? = null,
    val conditionId:String? = null,
    val matchCount:Int? = null,
    val refinementHref: String? = null
)

@Serializable
data class Warning(
    val category:String? = null,
    val domain:String? = null,
    val errorId:Int? = null,
    val inputRefIds:List<String>? = null,
    val longMessage:String? = null,
    val message:String? = null,
    val outputRefIds:List<String>? = null,
    val parameters:List<ErrorParameters>? = null,
    val subdomain:String? = null,
)

@Serializable
data class ErrorParameters(
    val name:String? = null,
    val value:String? = null
)