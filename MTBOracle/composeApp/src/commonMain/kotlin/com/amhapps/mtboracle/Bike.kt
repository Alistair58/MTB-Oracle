import kotlinx.serialization.Serializable
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round
@Serializable
data class BikeData(
    var year:Int = -1,
    var brand: String = "",
    var model: String = "",
    var category: String = "",
    var condition: String = "",
    var size: String = "",
    var wheelSize: String = "",
    var material: String = "",
    var frontTravel: Int = -1,
    var rearTravel: Int = -1,
    var country: String = ""
)

class Bike(
    private var bikeData: BikeData
) {
    private val name = "${bikeData.year} ${bikeData.brand} ${bikeData.model}"
        get() = field


    val values: FloatArray
        get() {
            var brandInfo = arrayOfNulls<Float>(6)
            var modelInfo = arrayOfNulls<Float>(6)
            var countryInfo = arrayOfNulls<Float>(6)
            //brandInfo = Dataset.getBrandStats(brand).toArray(brandInfo) //min,LQ,median,UQ,max,SD
            //modelInfo = Dataset.getModelStats(brand, model).toArray(modelInfo)
            //countryInfo = Dataset.getCountryStats(country).toArray(countryInfo)

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

    private fun encodeYear(year: Int): Int {
        val encoded = if (year >= 1960 && year < 1970) 1
        else if (year >= 1970 && year < 1986) 2
        else if (year >= 1986 && year < 1991) 3
        else if (year >= 1991 && year < 1996) 4
        else if (year >= 1996 && year < 2001) 5
        else if (year >= 2001 && year < 2030) 6 + (year - 2001)
        else 0
        return encoded
    }


    private fun encodeCategory(category: String?): Int {
        if (null == category) return 0
        val encoded = when (category) {
            "xc" -> 1
            "trail" -> 2
            "enduro" -> 3
            "downhill" -> 4
            "dirt" -> 5
            "kids" -> 6
            "vintage" -> 7
            "ebikes" -> 8
            "gravel/cx" -> 9
            "fat" -> 10
            "trials" -> 11
            else -> 0
        }
        return encoded
    }

    private fun encodeCondition(condition: String?): Int {
        if (null == condition) return 0
        val encoded = when (condition) {
            "new" -> 1
            "excellent" -> 2
            "good" -> 3
            "poor" -> 4
            "for" -> 5
            else -> 0
        }
        //        if(encoded==0) System.out.println("Could not encode condition: "+condition);
        return encoded
    }

    private fun encodeSize(size: String?): Int {
        if (null == size) return 0
        val encoded = when (size) {
            "xs", "12", "13", "14", "43cm", "44cm", "45cm", "46cm", "47cm", "48cm", "49cm", "50cm" -> 1
            "s", "15", "15.5", "16", "16.5", "51cm", "52cm", "53cm" -> 2
            "m", "17", "17.5", "18", "18.5", "54cm", "55cm" -> 3
            "l", "19", "19.5", "20", "20.5", "56cm", "57cm", "58cm" -> 4
            "xl", "21", "21.5", "22", "22.5", "59cm", "60cm" -> 5
            "xxl", "23", "24", "25", "26", "27", "28", "61cm", "62cm", "63cm", "64cm", "65cm" -> 6
            else -> 0
        }
        return encoded
    }

    private fun encodeWheelSize(wheelSize: String?): Int {
        if (null == wheelSize) return 0
        val encoded = when (wheelSize) {
            "16" -> 1
            "20" -> 2
            "24" -> 3
            "26" -> 4
            "27.5" -> 5
            "29" -> 6
            "650c" -> 7
            "700c" -> 8
            else -> 0
        }
        return encoded
    }

    private fun encodeMaterial(material: String?): Int {
        if (null == material) return 0
        val encoded = when (material) {
            "aluminium" -> 1
            "carbon" -> 2
            "chromoly" -> 3
            "steel" -> 4
            "titanium" -> 5
            else -> 0
        }
        //        if(encoded==0) System.out.println("Could not encode material: "+material);
        return encoded
    }

    private fun encodeTravel(travel: Int?): Int {
        var encoded:Int
        if (null == travel || travel < 0) return 0
        try {
            encoded =
                round(travel.toFloat() / 10.0).toInt() //round to nearest ten and divide by 10 e.g. 123 -> 12
        } catch (e: NumberFormatException) {
            println("Could not encode travel: $travel") //A lot of listings don't have travel
            return 0
        }
        encoded = min(encoded.toDouble(), 25.0).toInt()
        encoded = max(encoded.toDouble(), 0.0).toInt()
        return encoded + 1 //reserve neuron 0 for no travel info -therefore max is actually 26
    }
}