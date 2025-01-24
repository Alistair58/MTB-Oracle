import kotlin.math.floor

abstract class Dataset {
    protected var modelStats: Map<String, Map<String, ArrayList<Float>>>? = null
    protected var countryStats: Map<String, ArrayList<Float>>? = null
    protected val exchangeRates: HashMap<String, Float> =
        HashMap<String, Float>()

    protected abstract fun loadModelStats()

    protected abstract fun loadCountryStats()

    protected abstract fun loadExchangeRates()


    fun getExchangeRate(currency: String): Float {
        if (currency == "GBP") return 1f
        val query:Float? = exchangeRates.get(currency)
        //Elvis operator - Checks for nullability. If not null, returns value. If null, returns -1
        return query ?: -1f
    }

    fun getCountryStats(rawCountry: String): ArrayList<Float> {
        var country = when(rawCountry){
            "United Kingdom UK"->"united kingdom" //Only edge cases 23/01/25
            "United States of America USA"->"united states"
            else -> rawCountry
        }
        country = country.lowercase()
        val stats: ArrayList<Float> = countryStats!![country]?:ArrayList<Float>()
        if (stats.size == 0) {
            println("Could not get stats for $country")
            return modelStats!![""]!![""]?:ArrayList<Float>()
        }
        return stats
    }

    fun getBrandStats(brand: String):ArrayList<Float> {
        var brand = brand
        brand = brand.lowercase()
        val brandStats: Map<String, ArrayList<Float>>? = modelStats!![brand]
        return if (null == brandStats) modelStats!![""]!![""]?:ArrayList<Float>()
        else brandStats[""]?:ArrayList<Float>() //It won't be null
    }

    fun getModelStats(brand: String, model: String): ArrayList<Float> {
        var brand = brand
        var model = model
        brand = brand.lowercase() //If it wasn't already
        model = model.lowercase()
        val brandStats: Map<String, ArrayList<Float>>? = modelStats!![brand]
        if (null == brandStats) return modelStats!![""]!![""]?:ArrayList<Float>() //It won't be null - average stats across everything
        else {
            val stats: ArrayList<Float>? = brandStats[model]
            if (null == stats) { //if model doesn't exist
                //float initialValuation = n.process(bike.getModelessValues());
                val firstWord = model.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()[0].replace(
                    "[^a-zA-Z]".toRegex(),
                    ""
                ) //Also works for the case where it is just a single word
                var similarCount = 0
                val similarStats: ArrayList<ArrayList<Float>> =
                    ArrayList<ArrayList<Float>>(
                            arrayListOf( ArrayList<Float>(),
                            ArrayList<Float>(),
                            ArrayList<Float>(),
                            ArrayList<Float>(),
                            ArrayList<Float>(),
                            ArrayList<Float>())
                    ) //medians are better than means - despite the faf
                for (otherModel in brandStats.keys) {
                    val otherStats: ArrayList<Float> = brandStats[otherModel]?:ArrayList<Float>()
                    if (otherModel.startsWith(firstWord)) { // && (Math.abs(initialValuation-otherStats.get(2))*100/initialValuation)<100){
                        for (i in similarStats.indices) {
                            similarStats.get(i).add(otherStats.get(i))
                        }
                        similarCount++
                    }
                }
                if (similarCount == 0) return brandStats[""]?:ArrayList<Float>()
                val medianStats: ArrayList<Float> = ArrayList<Float>(
                    mutableListOf<Float>(0f, 0f, 0f, 0f, 0f, 0f)
                )
                val lengthEach: Int = similarStats.get(0).size //all have same length
                val midpoint = floor((lengthEach / 2).toDouble()).toInt()
                for (i in 0 until similarStats.size - 1) {
                    val similarStat: ArrayList<Float> = similarStats.get(i)
                    similarStat.sort()
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