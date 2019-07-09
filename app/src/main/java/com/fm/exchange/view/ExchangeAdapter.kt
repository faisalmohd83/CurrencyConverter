package com.fm.exchange.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fm.excahnge.util.image.ImageFetcherUtilImpl
import com.fm.excahnge.util.number.NumberFormatterUtilImpl
import com.fm.exchange.R
import com.fm.exchange.model.Currency
import kotlinx.android.synthetic.main.exchange_list_row.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class ExchangeAdapter internal constructor(
    itemTapListener: OnListItemTapListener
) :
    RecyclerView.Adapter<ExchangeAdapter.ViewHolder>(), KoinComponent {

    private val TAG = "ExchangeAdapter"

    private val mContext: Context by inject()
    private val mImageUtil: ImageFetcherUtilImpl by inject()
    private val mFormatterUtil: NumberFormatterUtilImpl by inject()

    private var mCurrencies = ArrayList<Currency>()
    private var mOnItemTapListener: OnListItemTapListener = itemTapListener

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun getItemCount() = mCurrencies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.exchange_list_row, parent, false) as View
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currency = mCurrencies[position]

        // flag icon
        val url = currency.flag_ulr
        url.let { mImageUtil.fetchRemoteSVGImage(it!!, holder.view.imgCountryFlag) }

        // currency code
        holder.view.tvCurrencyCode.text = currency.code

        // currency name
        holder.view.tvCurrencyName.text = currency.name

        // currency rate
        holder.view.etAmount.setText("${currency.rate}")
        if (position == 0) {
            holder.view.etAmount.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE /*|| event== KeyEvent.ACTION_UP*/) {
                    updateListOnUserInput(currency.code!!, holder.view.etAmount.text.toString())
                    return@setOnEditorActionListener true
                }
                false
            }

            /*holder.view.etAmount.setOnKeyListener(View.OnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                    //Perform Code
                    updateListOnUserInput(currency.code, holder.view.etAmount.text.toString())
                    return@OnKeyListener true
                }
                false
            })*/
        } else {
            // on tap
            holder.view.setOnClickListener {
                Log.d(TAG, "Clicked on $position")
                updateListOnUserInput(currency.code!!, holder.view.etAmount.text.toString())
            }
        }
    }

    /**
     * Method to initiate the remote query to update list
     */
    private fun updateListOnUserInput(currency: String, rate: String) {
        Log.d(TAG, "updateListOnUserInput { baseCurrency : $currency , baseRate: $rate}")
        mOnItemTapListener.onItemTapped(
            currency,
            mFormatterUtil.getAdjustedCurrencyRate(rate.toDouble())
        )
    }

    /**
     * Method to update adapter with supplied data, received from remote
     */
    fun updateAdapter(currencies: List<Currency>) {
        val diffCallback = CurrencyDiffCallback(mCurrencies, currencies)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        mCurrencies.clear()
        mCurrencies.addAll(currencies)
        diffResult.dispatchUpdatesTo(this)
    }
}
