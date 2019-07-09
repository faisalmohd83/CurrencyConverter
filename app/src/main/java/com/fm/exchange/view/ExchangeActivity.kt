package com.fm.exchange.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.fm.exchange.R
import com.fm.exchange.model.Currency
import kotlinx.android.synthetic.main.activity_exchange.*
import java.util.*

class ExchangeActivity : AppCompatActivity(), OnListItemTapListener {

    private val TAG = "ExchangeActivity"

    private lateinit var mAdapter: ExchangeAdapter
    private lateinit var viewModel: ExchangeViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange)

        // View Model
        viewModelFactory = ViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExchangeViewModel::class.java)

        mAdapter = ExchangeAdapter(this, this)

        rvExchangeList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onResume() {
        super.onResume()
        // Listen to data change
        viewModel.getExchangeRatesList().observe(this, mListObserver)
    }

    private val mListObserver: Observer<List<Currency>> = Observer { list ->
        updateUI(list.isNullOrEmpty(), list)
    }

    /**
     *
     */
    private fun updateUI(
        isEmpty: Boolean,
        currencyList: List<Currency>
    ) {
        if (isEmpty) {
            pbLoading.visibility = View.VISIBLE
            rvExchangeList.visibility = View.GONE
        } else {
            mAdapter.updateAdapter(currencyList)
            pbLoading.visibility = View.GONE
            rvExchangeList.visibility = View.VISIBLE
        }
    }

    override fun onPause() {
        viewModel.getExchangeRatesList().removeObserver(mListObserver)
        super.onPause()
    }

    /**
     *
     */
    override fun onItemTapped(currencyCode: String, currencyValue: String) {
        Log.d(TAG, "Currency: $currencyCode , Value: $currencyValue")
        viewModel.updateQuery(currencyCode, currencyValue)
        rvExchangeList.smoothScrollToPosition(0)
    }

    /**
     * method hides soft keyboard
     */
    private fun hideKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(Objects.requireNonNull(currentFocus)?.windowToken, 0)
    }

    override fun onStop() {
        viewModel.onStop()
        super.onStop()
    }
}
