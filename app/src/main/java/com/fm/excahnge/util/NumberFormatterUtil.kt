package com.fm.excahnge.util

import java.text.NumberFormat
import java.util.*

/**
 * Helper class to manipulate the Numbers
 */
object NumberFormatterUtil {

    /**
     * Method to format supplied value into currency format, [i.e, 1234.56]
     *
     * @param currencyValue to be formatted
     * @param baseRate to manipulate with supplied currencyValue
     *
     * @return the value in currency format
     */
    fun getAdjustedCurrencyRate(currencyValue: Double, baseRate: Double): Double {

        // TODO: do async
        val nf = NumberFormat.getNumberInstance(Locale.getDefault())
        nf.maximumFractionDigits = 2
        return try {
            nf.format(currencyValue * baseRate).replace(",", "").toDouble()
        } catch (exception: NumberFormatException) {
            throw NumberFormatException()
        }
    }
}