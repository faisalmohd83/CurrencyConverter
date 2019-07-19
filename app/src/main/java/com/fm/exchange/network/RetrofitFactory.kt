package com.fm.exchange.network

import android.util.Log
import com.fm.exchange.BuildConfig
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Factory class for [ApiEndpoints]
 */
class RetrofitFactory {

    private val TAG = "RetrofitFactory"

    /**
     * Factory method to return the api endpoint.
     */
    fun create(): ApiEndpoints {
        Log.d(TAG, "RetrofitFactory.create()")

        // create an RxJava Adapter, network calls made asynchronous
        val rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

        // http interceptor
        val httpClient = prepareHttpInterceptor()

        // retrofit object
        val retrofit = prepareRetrofitInstance(httpClient, rxAdapter)

        return retrofit.create(ApiEndpoints::class.java)
    }

    /**
     *
     */
    private fun prepareRetrofitInstance(
        httpClient: OkHttpClient,
        callAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit {
        Log.d(TAG, "prepareRetrofitInstance()")

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(callAdapterFactory)
            .build()
    }

    /**
     *
     */
    private fun prepareHttpInterceptor(): OkHttpClient {
        Log.d(TAG, "prepareHttpInterceptor()")

        val interceptor = HttpLoggingInterceptor()
        when {
            BuildConfig.DEBUG -> interceptor.level = HttpLoggingInterceptor.Level.BODY
            else -> interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    companion object {
        /**
         * Base Url to fetch the exchange rates for the supplied currency.
         */
        private const val BASE_URL = "https://revolut.duckdns.org/"
    }
}
