package com.amhapps.mtboracle

import Dataset
import android.content.Context

class AndroidDataset(private val context: Context):Dataset() {
    override fun loadModelStats() {
        try {
            val fHand = context.assets.open("neural-network/modelStatsV3TaT.json")
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
        } catch (e: java.io.IOException) {
            println(e)
        }
    }

    override fun loadCountryStats() {
        try {
            val fHand = context.assets.open("neural-network/countryStatsV3TaT.json")
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
        } catch (e: java.io.IOException) {
            println(e)
        }
    }

    override fun loadExchangeRates() {
        try {
            val fHand = context.assets.open("neural-network/average_csv_2024-3.csv")
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
        } catch (e: java.lang.Exception) {
            println(e)
        }
    }
}