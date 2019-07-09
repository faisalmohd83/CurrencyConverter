package com.fm.exchange.common

import com.fm.exchange.model.Response
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpoints {

    /**
     * API to fetch the exchange rates for the supplied currency. The sample API call would be
     * https://revolut.duckdns.org/latest?base={@valiteral baseCurrency}.
     *
     * @param baseCurrency for user authentication.
     * @return list of Movies in [MoviesList] format.
     */
    @GET("latest")
    fun getExchangeRates(@Query("base") baseCurrency: String = "EUR"): Observable<Response>

}
