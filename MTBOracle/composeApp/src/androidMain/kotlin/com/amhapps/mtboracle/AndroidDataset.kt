package com.amhapps.mtboracle

import Dataset
import android.content.Context
import capitalise

class AndroidDataset(private val context: Context):Dataset() {
    init {
        loadModelStats()
        loadCountryStats()
        loadExchangeRates()
    }

    override fun loadModelStats() {
        try {
            val fHand = context.assets.open("modelStatsV3TaT.json")
            val reader: java.util.Scanner = java.util.Scanner(fHand, "UTF-8")
            var data = ""
            while (reader.hasNextLine()) {
                data += reader.nextLine()
            }
            reader.close()
            val builder: com.google.gson.GsonBuilder = com.google.gson.GsonBuilder()
            val gson: com.google.gson.Gson = builder.create()
            val type: java.lang.reflect.Type = object :
                com.google.gson.reflect.TypeToken<Map<String?, Map<String?, java.util.ArrayList<Float?>?>?>?>() {}.getType()
            modelStats =
                gson.fromJson<Map<String, Map<String, java.util.ArrayList<Float>>>>(data, type)
        } catch (e: Exception) {
            println(e)
        }
    }

    override fun loadCountryStats() {
        try {
            val fHand = context.assets.open("countryStatsV3TaT.json")
            val reader: java.util.Scanner = java.util.Scanner(fHand, "UTF-8")
            var data = ""
            while (reader.hasNextLine()) {
                data += reader.nextLine()
            }
            reader.close()
            val builder: com.google.gson.GsonBuilder = com.google.gson.GsonBuilder()
            val gson: com.google.gson.Gson = builder.create()
            val type: java.lang.reflect.Type = object :
                com.google.gson.reflect.TypeToken<Map<String?, java.util.ArrayList<Float?>?>?>() {}.getType()
            countryStats = gson.fromJson<Map<String, java.util.ArrayList<Float>>>(data, type)
        } catch (e: Exception) {
            println(e)
        }
    }

    override fun loadExchangeRates() {
        try {
            val fHand = context.assets.open("average_csv_2024-3.csv")
            val reader: java.util.Scanner = java.util.Scanner(fHand, "UTF-8")
            var data = ""
            while (reader.hasNextLine()) {
                data += reader.nextLine()
            }
            reader.close()
            val exchangeRatesCsv: List<String> = java.util.Arrays.asList<String>(
                *data.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray())
            var i = 7
            while (i < exchangeRatesCsv.size) {
                //See CSV file for indices
                exchangeRates.put(exchangeRatesCsv[i], exchangeRatesCsv[i + 1].toFloat())
                i += 5
            }
        } catch (e: Exception) {
            println(e)
        }
    }

    fun findModels(brand: String?):List<String>{
        if(null == brand) return emptyList()
        val brandWords = brand.lowercase().trim().split(" ")
        val brandFirst = brandWords[0]
        val modelsMap = modelStats!![brandFirst]
        try{
            val possibleModels: List<String> = modelsMap?.keys?.toList() ?: emptyList()
            if(possibleModels.isEmpty()) return emptyList()
            if(brandWords.size == 1) return capitalise(possibleModels)
            val result:MutableList<String> = mutableListOf()
            for(pModel in possibleModels){
                if(pModel.startsWith(brandWords.slice(1..<brandWords.size).joinToString(" "))){
                    val pModelWords = pModel.split(" ")
                    if(pModelWords.size > 1){
                        result.add(pModelWords[brandWords.size - 1])
                    }

                }
            }
            return capitalise(result)
        }
        catch (e:Exception){
            return emptyList()
        }

    }


}