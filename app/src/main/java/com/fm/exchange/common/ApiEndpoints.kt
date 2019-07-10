package com.fm.exchange.common

import com.fm.exchange.model.Response
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Class to accommodate all API end points
 */
interface ApiEndpoints {

    /**
     * API to fetch the exchange rates for the supplied base currency. The sample API call would be
     * https://revolut.duckdns.org/latest?base={@valiteral baseCurrency}
     *
     * @param baseCurrency for user authentication
     *
     * @return list of exchange rates in {@Response} format
     */
    @GET("latest")
    fun getExchangeRates(@Query("base") baseCurrency: String): Observable<Response>

}
