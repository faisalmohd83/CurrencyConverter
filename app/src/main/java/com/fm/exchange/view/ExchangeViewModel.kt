package com.fm.exchange.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fm.excahnge.util.GenericUtilsImpl
import com.fm.excahnge.util.number.NumberFormatterUtilImpl
import com.fm.exchange.common.ApiEndpoints
import com.fm.exchange.common.RetrofitFactory
import com.fm.exchange.model.Currency
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.TimeUnit

class ExchangeViewModel : ViewModel(), KoinComponent {

    private val TAG = "ExchangeViewModel"

    private var mBaseCurrency = "EUR"
    private var mBaseRate = 1.0

    private var currencyList = MutableLiveData<List<Currency>>().apply { emptyList<Currency>() }

    private lateinit var mNetworkTimerDisposable: Disposable
    private lateinit var mDataFetcherDisposable: Disposable

    private val mGenericUtils: GenericUtilsImpl by inject()
    private val mFormatterUtil: NumberFormatterUtilImpl by inject()

    init {
        initTimedAPIRequest()
//       var repository= ExchangeRepository.instance()
    }

    /**
     *
     */
    fun onStart() {
        initTimedAPIRequest()
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
    private fun initTimedAPIRequest() {
        Log.d(TAG, "initTimedAPIRequest()")

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
            list.add(0, mGenericUtils.getCurrencyObject(mBaseCurrency, mBaseRate))

            currencies.forEach { (currencyCode, receivedRate) ->
                adjustedRate = mFormatterUtil.getAdjustedCurrencyRate(receivedRate * mBaseRate)
                Log.d(TAG, "value: {$receivedRate} , valueAdjusted: {$adjustedRate}")
                list.add(mGenericUtils.getCurrencyObject(currencyCode, adjustedRate))
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
    fun updateQuery(baseCurrency: String, baseValue: Double) {
        Log.d(TAG, "updateQuery { baseCurrency : $baseCurrency , baseRate: $baseValue}")

        mBaseCurrency = baseCurrency
        mBaseRate = baseValue

        freeCachedMemory()
        initTimedAPIRequest()
    }

    /**
     *
     */
    fun onStop() {
        onCleared()
    }

}