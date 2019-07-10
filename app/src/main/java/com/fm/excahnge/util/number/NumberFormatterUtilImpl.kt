package com.fm.excahnge.util.number

import java.text.DecimalFormat

/**
 * Helper class to manipulate the Numbers
 */
class NumberFormatterUtilImpl : NumberFormatterUtil {

    /**
     * Method to format supplied value into currency format, [i.e, 1234.56]
     *
     * @param currencyValue to be formatted
     *
     * @return the value in currency format
     */
    override fun getAdjustedCurrencyRate(currencyValue: Double): Double {
        return DecimalFormat("0.00").format(currencyValue).toDouble()
    }
}