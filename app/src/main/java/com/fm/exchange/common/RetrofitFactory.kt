package com.fm.exchange.model.common

import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Factory class for [ApiEndpoints].
 */
object RetrofitFactory {

    /**
     * Base Url to fetch the exchange rates for the supplied currency.
     */
    private const val BASE_URL = "https://revolut.duckdns.org/"


    fun create(): ApiEndpoints {

        // create an RxJava Adapter, network calls made asynchronous
        val rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

        // Build retrofit object
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(rxAdapter)
            .build()

        return retrofit.create(ApiEndpoints::class.java)
    }
}
