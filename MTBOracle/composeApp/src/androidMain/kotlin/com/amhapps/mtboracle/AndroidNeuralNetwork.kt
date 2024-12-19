package com.amhapps.mtboracle

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import android.content.Context

class AndroidNeuralNetwork(private val context: Context): NeuralNetwork(){
    override var weights: Array<Array<FloatArray>>
    override var activations: Array<FloatArray>
    init {
        this.weights = loadWeights()
        this.activations = loadActivations()
    }


    override fun loadWeights(): Array<Array<FloatArray>>{
        var lWeights = Array(0) { Array(0) { FloatArray(0) } }
        try {
            val fHand = context.assets.open("weights.json")   //("resources/NeuralNetworkRes/weights.json")
            val myReader: java.util.Scanner = java.util.Scanner(fHand)
            var data = ""
            while (myReader.hasNextLine()) {
                data += myReader.nextLine()
            }
            myReader.close()
            val builder: GsonBuilder = GsonBuilder()
            val gson : Gson =  builder.create()
            lWeights = gson.fromJson(data, Array<Array<FloatArray>>::class.java)
        } catch (e: Exception) {
            println(e)
        }
        return lWeights
    }

}