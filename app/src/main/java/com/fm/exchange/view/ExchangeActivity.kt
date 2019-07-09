package com.fm.exchange

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exchange.*

class ExchangeActivity : AppCompatActivity() {

    private val TAG = "MovieListFragment"

    private lateinit var mAdapter: ExchangeAdapter
    private lateinit var viewModel: ExchangeViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange)

        // View Model
        viewModelFactory = ViewModelFactory()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExchangeViewModel::class.java)

        mAdapter = ExchangeAdapter()

        rvExchangeList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "onResume()")

        // Listen to data change
        viewModel.getExchangeRates().observe(this, mListObserver)
    }

    private val mListObserver: Observer<List<MovieItem>> = Observer { items ->
        Log.d(TAG, "MovieItems: ${items.size}")
        mAdapter.submitList(items)
    }

    override fun onPause() {
        viewModel.getExchangeRates().removeObserver(mListObserver)

        Log.d(TAG, "onPause()")
        super.onPause()
    }
}
