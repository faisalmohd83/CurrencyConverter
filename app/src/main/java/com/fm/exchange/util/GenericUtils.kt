package com.fm.excahnge.util

import com.fm.exchange.model.Currency

/**
 *
 */
interface GenericUtils {

    fun getCurrencyObject(currencyCode: String, CurrencyRate: Double): Currency

}
