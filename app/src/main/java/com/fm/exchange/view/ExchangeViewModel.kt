package com.fm.exchange.view

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.fm.excahnge.util.NumberFormatterUtil
import com.fm.excahnge.util.Utils
import com.fm.exchange.common.ApiEndpoints
import com.fm.exchange.common.RetrofitFactory
import com.fm.exchange.model.Currency
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ExchangeViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "ExchangeViewModel"

    private var mBaseCurrency = "EUR"
    private var mBaseRate = 1.0

    private var currencyList = MutableLiveData<List<Currency>>().apply { emptyList<Currency>() }

    private lateinit var mNetworkTimerDisposable: Disposable
    private lateinit var mDataFetcherDisposable: Disposable

    init {
        fetchExchangeRates()
//       var repository= ExchangeRepository.instance()
    }

    /**
     *
     */
    fun onStart() {
        fetchExchangeRates()
    }

    /**
     *
     */
    fun getExchangeRatesList(): MutableLiveData<List<Currency>> {
        return currencyList
    }

    /**
     *
     */
    private fun fetchExchangeRates() {
        Log.d(TAG, "fetchExchangeRates()")

        mNetworkTimerDisposable = Observable.interval(0, 5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { callAPIEndPointsOnSuccess() },
                { onError(it) })
    }

    /**
     *
     */
    private fun callAPIEndPointsOnSuccess() {
        Log.d(TAG, "callAPIEndPointsOnSuccess()")

        val api: ApiEndpoints = RetrofitFactory.create()
        Log.d(
            TAG,
            "callAPIEndPointsOnSuccess { baseCurrency : $mBaseCurrency , baseRate: $mBaseRate}"
        )
        val observable = api.getExchangeRates(mBaseCurrency)

        mDataFetcherDisposable = observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { handleSuccess(it.rates) },
                { onError(it) }
            )
    }

    /**
     *
     */
    private fun handleSuccess(currencies: Map<String, Double>) {
        Log.d(TAG, "handleSuccess()")

        if (currencies.isNotEmpty()) {
            Log.d(TAG, "{${currencies.size}}")

            var adjustedRate: Double
            val list = arrayListOf<Currency>()

            // set base values
            list.add(0, Utils.getCurrencyObject(mBaseCurrency, mBaseRate, getApplication()))

            currencies.forEach { (currencyCode, receivedRate) ->

                adjustedRate = try {
                    NumberFormatterUtil.getAdjustedCurrencyRate(receivedRate, mBaseRate)
                } catch (exception: NumberFormatException) {
                    Log.e(TAG, exception.printStackTrace().toString())
                    receivedRate * mBaseRate
                }
                Log.d(TAG, "value: {$receivedRate} , valueAdjusted: {$adjustedRate}")

                list.add(Utils.getCurrencyObject(currencyCode, adjustedRate, getApplication()))
            }
            currencyList.postValue(list)

        } else {
            Log.e(TAG, "handleSuccess() - list is empty")
        }
    }

    /**
     *
     */
    private fun onError(throwable: Throwable) {
        Log.e(TAG, throwable.printStackTrace().toString())
        currencyList.postValue(null)
    }

    /**
     *
     */
    private fun freeCachedMemory() {
        mNetworkTimerDisposable.dispose()
        mDataFetcherDisposable.dispose()
    }

    /**
     *
     */
    override fun onCleared() {
        freeCachedMemory()
        super.onCleared()
    }

    /**
     *
     */
    fun updateQuery(baseCurrency: String, baseValue: String) {
        Log.d(TAG, "updateQuery { baseCurrency : $baseCurrency , baseRate: $baseValue}")

        mBaseCurrency = baseCurrency
        mBaseRate = baseValue.toDouble()

        freeCachedMemory()
        fetchExchangeRates()
    }

    /**
     *
     */
    fun onStop() {
        onCleared()
    }

}