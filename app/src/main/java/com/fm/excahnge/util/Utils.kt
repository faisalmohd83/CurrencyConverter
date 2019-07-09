package com.fm.excahnge.util

import android.content.Context
import android.util.Log
import com.fm.exchange.model.Currency
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

/**
 * Helper class accommodate common Util methods
 */
object Utils {

    private val TAG = "Utils"

    /**
     * Method to prepare the @{Currency} object with supplied parameters
     *
     * @param currencyCode to prepare the @{Currency} object
     * @param CurrencyRate to prepare the @{Currency} object
     * @param context to prepare the @{Currency} object
     *
     * @return @{Currency} object
     */
    fun getCurrencyObject(currencyCode: String, CurrencyRate: Double, context: Context): Currency {

        // TODO: do async
        val jsonObject: JSONObject?
        val jsonArray: JSONArray?

        try {
            jsonObject = JSONObject(readJSONFromAsset(context))
            jsonArray = jsonObject.getJSONArray("countries")
        } catch (exception: Exception) {
            // TODO: handle the exception appropriately
            return Currency(null, null, null, null)
        }

        var flagUrl: String? = null
        var currencyName: String? = null

        for (i in 0 until jsonArray.length()) {
            val jb = jsonArray.get(i) as JSONObject
            if (jb.getString("currency-code") == currencyCode) {
                currencyName = jb.getString("currency-name")
                flagUrl = jb.getString("flag-url")
            }
        }

        Log.d(TAG, "Code: $currencyCode , Currency: $currencyName , Flag : $flagUrl")

        return Currency(currencyCode, currencyName, CurrencyRate, flagUrl)
    }

    /**
     * Util method to read the file from assets
     *
     * @param context to read the assets
     *
     * @return read file content as String
     */
    private fun readJSONFromAsset(context: Context): String {
        return try {
            val inputStream: InputStream = context.assets.open("country_details.json")
            inputStream.bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            Log.e(TAG, "Caused $ioException, thrown back to caller")
            throw Exception()
        }
    }

}