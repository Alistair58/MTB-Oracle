import kotlin.math.floor

internal class Dataset {
    init {
        loadModelStats()
        loadCountryStats()
        loadExchangeRates()
    }


    private fun loadModelStats() {
        try {
            val file: java.io.File = java.io.File("resources/NeuralNetworkRes/modelStatsV3TaT.json")
            val reader: java.util.Scanner = java.util.Scanner(file, "UTF-8")
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

    private fun loadCountryStats() {
        try {
            val file: java.io.File =
                java.io.File("resources/NeuralNetworkRes/countryStatsV3TaT.json")
            val reader: java.util.Scanner = java.util.Scanner(file, "UTF-8")
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


    private fun loadExchangeRates() {
        try {
            val file: java.io.File =
                java.io.File("resources/NeuralNetworkRes/average_csv_2024-3.csv")
            val reader: java.util.Scanner = java.util.Scanner(file, "UTF-8")
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


    companion object {
        private var modelStats: Map<String, Map<String, java.util.ArrayList<Float>>>? = null
        private var countryStats: Map<String, java.util.ArrayList<Float>>? = null
        private val exchangeRates: java.util.HashMap<String, Float> =
            java.util.HashMap<String, Float>()

        fun getExchangeRate(currency: String): Float {
            if (currency == "GBP") return 1
            var rate: Float = exchangeRates.get(currency)
            if (null == rate) {
                //System.out.println("Could not get exchange rate for "+currency);
                rate = -1f
            }
            return rate
        }


        fun getCountryStats(country: String): java.util.ArrayList<Float>? {
            var country = country
            country = country.lowercase(java.util.Locale.getDefault())
            val stats: java.util.ArrayList<Float>? = countryStats!![country]
            if (null == stats) {
                println("Could not get stats for $country")
                return modelStats!![""]!![""]
            }
            return stats
        }


        fun getBrandStats(brand: String): java.util.ArrayList<Float>? {
            var brand = brand
            brand = brand.lowercase(java.util.Locale.getDefault())
            val brandStats: Map<String, java.util.ArrayList<Float>>? = modelStats!![brand]
            return if (null == brandStats) modelStats!![""]!![""]
            else brandStats[""]
        }

        fun getModelStats(brand: String, model: String): java.util.ArrayList<Float>? {
            var brand = brand
            var model = model
            brand = brand.lowercase(java.util.Locale.getDefault()) //If it wasn't already
            model = model.lowercase(java.util.Locale.getDefault())
            val brandStats: Map<String, java.util.ArrayList<Float>>? = modelStats!![brand]
            if (null == brandStats) return modelStats!![""]!![""] //average stats across everything
            else {
                val stats: java.util.ArrayList<Float>? = brandStats[model]
                if (null == stats) { //if model doesn't exist
                    //float initialValuation = n.process(bike.getModelessValues());
                    val firstWord = model.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray()[0].replace(
                        "[^a-zA-Z]".toRegex(),
                        ""
                    ) //Also works for the case where it is just a single word
                    var similarCount = 0
                    val similarStats: java.util.ArrayList<java.util.ArrayList<Float>> =
                        java.util.ArrayList<java.util.ArrayList<Float>>(
                            java.util.Arrays.asList<java.util.ArrayList<Float>>(
                                java.util.ArrayList<Float>(),
                                java.util.ArrayList<Float>(),
                                java.util.ArrayList<Float>(),
                                java.util.ArrayList<Float>(),
                                java.util.ArrayList<Float>(),
                                java.util.ArrayList<Float>()
                            )
                        ) //medians are better than means - despite the faf
                    for (otherModel in brandStats.keys) {
                        val otherStats: java.util.ArrayList<Float> = brandStats[otherModel]
                        if (otherModel.startsWith(firstWord)) { // && (Math.abs(initialValuation-otherStats.get(2))*100/initialValuation)<100){
                            for (i in similarStats.indices) {
                                similarStats.get(i).add(otherStats.get(i))
                            }
                            similarCount++
                        }
                    }
                    if (similarCount == 0) return brandStats[""]
                    val medianStats: java.util.ArrayList<Float> = java.util.ArrayList<Float>(
                        mutableListOf<Float>(0f, 0f, 0f, 0f, 0f, 0f)
                    )
                    val lengthEach: Int = similarStats.get(0).size //all have same length
                    val midpoint = floor((lengthEach / 2).toDouble()).toInt()
                    for (i in 0 until similarStats.size - 1) {
                        val similarStat: java.util.ArrayList<Float> = similarStats.get(i)
                        similarStat.sort(null)
                        if ((lengthEach and 1) == 0) {
                            medianStats.set(
                                i,
                                (similarStat.get(midpoint - 1) + similarStat.get(midpoint)) / 2
                            )
                        } else {
                            medianStats.set(i, similarStat.get(midpoint))
                        }
                    }
                    //do mean for stdev otherwise it only every puts out 0 (as lots of models only have one bike)
                    var meanStdDev = 0f
                    for (i in 0 until lengthEach) {
                        meanStdDev += similarStats.get(5).get(i)
                    }
                    medianStats.set(5, meanStdDev / lengthEach)
                    return medianStats
                } else return stats
            }
        }
    }
}