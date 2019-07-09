package com.fm.exchange.network

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.fm.exchange.model.Currency
import io.reactivex.disposables.Disposable

class ExchangeRepository internal constructor(context: Context) {

    private val TAG = "ExchangeRepository"

    private var mBaseCurrency = "EUR"
    private var mBaseRate = 1.0
    private val mContext: Context = context

    private var currencyList = MutableLiveData<List<Currency>>().apply { emptyList<Currency>() }

    private lateinit var mNetworkTimerDisposable: Disposable
    private lateinit var mDataFetcherDisposable: Disposable

    /*companion object {
        private var sInstance: ExchangeRepository? = null
        fun instance(): ExchangeRepository {
            if (sInstance == null) {
                synchronized(ExchangeRepository) {
                    sInstance = ExchangeRepository()
                }
            }
            return sInstance!!
        }
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
                { callServiceEndpoint() },
                { onError(it) })
    }

    /**
     *
     */
    private fun callServiceEndpoint() {
        Log.d(TAG, "callServiceEndpoint()")

        val api: ApiEndpoints = RetrofitFactory.create()
        Log.d(TAG, "callServiceEndpoint { baseCurrency : $mBaseCurrency , baseRate: $mBaseRate}")
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
            list.add(0, GenericUtils.getCurrencyObject(mBaseCurrency, mBaseRate, mContext))

            currencies.forEach { (currencyCode, receivedRate) ->

                adjustedRate = try {
                    NumberFormatterUtil.getAdjustedCurrencyRate(receivedRate, mBaseRate)
                } catch (exception: NumberFormatException) {
                    Log.e(TAG, exception.printStackTrace().toString())
                    receivedRate * mBaseRate
                }
                Log.d(TAG, "value: {$receivedRate} , valueAdjusted: {$adjustedRate}")

                list.add(GenericUtils.getCurrencyObject(currencyCode, adjustedRate, mContext))
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
    }*/
}