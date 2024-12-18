package com.amhapps.mtboracle

import Bike
import BikeData

class AndroidBike(val bikeData: BikeData,val d:AndroidDataset): Bike(bikeData) {
    override fun getValues(): FloatArray{
        var brandInfo = ArrayList<Float>(6)
        var modelInfo = ArrayList<Float>(6)
        var countryInfo = ArrayList<Float>(6)
        brandInfo = d.getBrandStats(bikeData.brand) //min,LQ,median,UQ,max,SD
        modelInfo = d.getModelStats(bikeData.brand, bikeData.model)
        countryInfo = d.getCountryStats(bikeData.country)

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
        values[19] = encodeCondition(bikeData.condition).toFloat()
        values[20] = encodeCategory(bikeData.category).toFloat()
        values[21] = encodeSize(bikeData.size).toFloat()
        values[22] = encodeWheelSize(bikeData.wheelSize).toFloat()
        values[23] = encodeMaterial(bikeData.material).toFloat()
        values[24] = encodeTravel(bikeData.frontTravel).toFloat()
        values[25] = encodeTravel(bikeData.rearTravel).toFloat()

        return values
    }
}