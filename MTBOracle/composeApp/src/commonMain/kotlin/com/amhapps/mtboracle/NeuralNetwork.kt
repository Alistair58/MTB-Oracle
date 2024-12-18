package com.amhapps.mtboracle


abstract class NeuralNetwork {
    protected var weights: Array<Array<FloatArray>>
    protected var activations: Array<FloatArray>
    protected var numNeurones: IntArray = intArrayOf(147, 100, 1)
    protected var largestLayer: Int = 147
    protected val inputIndices: IntArray = intArrayOf(18, 53, 59, 71, 78, 87, 93, 120)

    init {
        this.weights = loadWeights()
        this.activations = loadActivations()
    }


    private fun resetActivations() {
        for (i in activations.indices) {
            for (j in activations[i].indices) {
                activations[i][j] = 0f
            }
        }
    }

    fun process(inputs: FloatArray): Float {
        var inputIndex: Int
        resetActivations()
        for (i in inputs.indices) {
            if (i < 18) { //just put the stats in
                activations[0][i] = inputs[i] //No reLU as the inputs are all positive
            } else { //one hot encode the rest
                inputIndex = inputIndices[i - 18] + inputs[i].toInt()
                activations[0][inputIndex] = 2000f //default value
            }
        }
        for (i in 1 until numNeurones.size) {
            for (j in 0 until numNeurones[i]) {
                var sum = 0f
                for (k in 0 until numNeurones[i - 1]) {
                    sum += weights[i - 1][j][k] * activations[i - 1][k]
                }
                sum += weights[i - 1][j][numNeurones[i - 1]] // Bias is last value
                activations[i][j] = relu(sum)
            }
        }
        return activations[numNeurones.size - 1][0] // final output
    }


    private fun relu(num: Float): Float {
        if (num <= 0) return 0f
        return num
    }


    abstract fun loadWeights(): Array<Array<FloatArray>>

    fun loadActivations(): Array<FloatArray> {
        return Array(numNeurones.size) { FloatArray(largestLayer) }
        //And yes the array is larger than needed
    }
}