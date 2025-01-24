package com.amhapps.mtboracle

import BikeData
import kotlinx.serialization.Serializable

@Serializable
class WebBikeData (
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
    override var country: String = "",
    override var price: String = ""
): BikeData{
    override fun isSameBike(bikeData: BikeData):Boolean{ //everything except price
        return year==bikeData.year && brand==bikeData.brand &&
                model==bikeData.model && category==bikeData.category &&
                condition==bikeData.condition && size==bikeData.size &&
                wheelSize==bikeData.wheelSize && material==bikeData.material &&
                frontTravel==bikeData.frontTravel && rearTravel==bikeData.rearTravel &&
                country==bikeData.country
    }
    companion object{ //static
        fun fromBikeData(bikeData: BikeData):WebBikeData{
            return WebBikeData(
                bikeData.year,
                bikeData.brand,
                bikeData.model,
                bikeData.category,
                bikeData.condition,
                bikeData.size,
                bikeData.wheelSize,
                bikeData.material,
                bikeData.frontTravel,
                bikeData.rearTravel,
                bikeData.country,
                bikeData.price
            )
        }
    }
}