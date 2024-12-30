package com.amhapps.mtboracle

import Bike
import BikeData
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Parcelize //Parcelize is android only and that's why bikeData is an interface
@Serializable
class AndroidBikeData (
    override var year:Int = -1,
    override var brand: String = "",
    override var model: String = "",
    override var category: String = "",
    override var condition: String = "",
    override var size: String = "",
    override var wheelSize: String = "",
    override var material: String = "",
    override var frontTravel: Float = -1f,
    override var rearTravel: Float = -1f,
    override var country: String = ""
): Parcelable, BikeData

class AndroidBike(val bikeData: BikeData,val d:AndroidDataset): Bike(bikeData) {
    override fun getValues(): FloatArray{
        var brandInfo = ArrayList<Float>(6)
        var modelInfo = ArrayList<Float>(6)
        var countryInfo = ArrayList<Float>(6)
        val brandWords = bikeData.brand.split(" ")
        val nnBrand = brandWords[0] //input allows multi-worded brands, nn doesn't
        var nnModel = bikeData.model
        if(brandWords.size>1){
            nnModel = ""
            for(i in 1..(brandWords.size-1)){
                nnModel+=" "+brandWords[i]
            }
            nnModel +=" "+ bikeData.model
        }
        var modelWords = nnModel.split(" ")
        if(modelWords.size > 2) nnModel = modelWords.slice(0..1).joinToString(" ")
        //everything (as of 28/12/24) in the dataset is a max of 2 words
        brandInfo = d.getBrandStats(nnBrand.lowercase().trim()) //min,LQ,median,UQ,max,SD
        modelInfo = d.getModelStats(nnBrand.lowercase().trim(), nnModel.lowercase().trim())
        countryInfo = d.getCountryStats(bikeData.country.lowercase().trim())

        val values = FloatArray(26)
        values[0] = brandInfo[0]!! //min BRAND
        values[1] = brandInfo[1]!! //LQ
        values[2] = brandInfo[2]!! //median
        values[3] = brandInfo[3]!! //UQ
        values[4] = brandInfo[4]!! //max
        values[5] = brandInfo[5]!! //SD

        values[6] = modelInfo[0]!! //min MODEL
        values[7] = modelInfo[1]!! //LQ
        values[8] = modelInfo[2]!! //median
        values[9] = modelInfo[3]!! //UQ
        values[10] = modelInfo[4]!! //max
        values[11] = modelInfo[5]!! //SD


        values[12] = countryInfo[0]!! //min COUNTRY
        values[13] = countryInfo[1]!! //LQ
        values[14] = countryInfo[2]!! //median
        values[15] = countryInfo[3]!! //UQ
        values[16] = countryInfo[4]!! //max
        values[17] = countryInfo[5]!! //SD

        values[18] = encodeYear(bikeData.year).toFloat()
        values[19] = encodeCondition(bikeData.condition.lowercase().trim()).toFloat()
        values[20] = encodeCategory(bikeData.category.lowercase().trim()).toFloat()
        values[21] = encodeSize(bikeData.size.lowercase().trim()).toFloat()
        values[22] = encodeWheelSize(bikeData.wheelSize.lowercase().trim()).toFloat()
        values[23] = encodeMaterial(bikeData.material.lowercase().trim()).toFloat()
        values[24] = encodeTravel(bikeData.frontTravel).toFloat()
        values[25] = encodeTravel(bikeData.rearTravel).toFloat()

        return values
    }
}


@Serializable
object AndroidBikeDataObject {
    val bikeData = object : NavType<AndroidBikeData>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): AndroidBikeData? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): AndroidBikeData {
            return Json.decodeFromString(Uri.decode(value)) //Uri again
        }

        override fun put(bundle: Bundle, key: String, value: AndroidBikeData) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: AndroidBikeData): String {
            return Uri.encode(Json.encodeToString(value)) //Uri is only an Android thing
        }

    }
}