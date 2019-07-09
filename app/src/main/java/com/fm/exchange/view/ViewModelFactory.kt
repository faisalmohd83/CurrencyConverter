package com.fm.exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExchangeViewModel::class.java)) {
            return ExchangeViewModel() as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}
