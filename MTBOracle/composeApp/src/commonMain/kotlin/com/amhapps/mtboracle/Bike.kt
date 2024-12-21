import kotlinx.serialization.Serializable
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round


interface  BikeData { //needs to be Serializable AND Parcelable in Android
    var year:Int
    var brand: String
    var model: String
    var category: String
    var condition: String
    var size: String
    var wheelSize: String
    var material: String
    var frontTravel: Float
    var rearTravel: Float
    var country: String
}

abstract class Bike(
    private var bikeData: BikeData
) {
    private val name = "${bikeData.year} ${bikeData.brand} ${bikeData.model}"
        get() = field


    abstract fun getValues(): FloatArray

    protected fun encodeYear(year: Int): Int {
        val encoded = if (year >= 1960 && year < 1970) 1
        else if (year >= 1970 && year < 1986) 2
        else if (year >= 1986 && year < 1991) 3
        else if (year >= 1991 && year < 1996) 4
        else if (year >= 1996 && year < 2001) 5
        else if (year >= 2001 && year < 2030) 6 + (year - 2001)
        else 0
        return encoded
    }


    protected fun encodeCategory(category: String?): Int {
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

    protected fun encodeCondition(condition: String?): Int {
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

    protected fun encodeSize(size: String?): Int {
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

    protected fun encodeWheelSize(wheelSize: String?): Int {
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

    protected fun encodeMaterial(material: String?): Int {
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

    protected fun encodeTravel(travel: Float?): Int {
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