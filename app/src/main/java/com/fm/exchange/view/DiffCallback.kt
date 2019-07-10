package com.fm.exchange.view

import androidx.recyclerview.widget.DiffUtil
import com.fm.exchange.model.Currency

class CurrencyDiffCallback(
    private val mOldList: List<Currency>,
    private val mNewList: List<Currency>
) :
    DiffUtil.Callback() {

    override fun getOldListSize() = mOldList.size

    override fun getNewListSize() = mNewList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        mOldList[oldItemPosition].rate == mNewList[newItemPosition].rate

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        mOldList[oldItemPosition].rate == mNewList[newItemPosition].rate

}