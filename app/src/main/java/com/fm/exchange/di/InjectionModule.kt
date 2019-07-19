package com.fm.exchange.di

import com.fm.exchange.data.ExchangeRepository
import com.fm.exchange.network.RetrofitFactory
import com.fm.exchange.util.GenericUtilsImpl
import com.fm.exchange.util.image.ImageFetcherUtilImpl
import com.fm.exchange.util.number.NumberFormatterUtilImpl
import com.fm.exchange.view.ExchangeActivity
import com.fm.exchange.view.ExchangeAdapter
import com.fm.exchange.view.ExchangeViewModel
import com.fm.exchange.view.OnListItemTapListener
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewModule = module {

    // ViewModel
    viewModel { ExchangeViewModel() }

    // Adapter
    factory { ExchangeAdapter(ExchangeActivity() as OnListItemTapListener) }
}

var dataModule = module {

    // Repository
    single { ExchangeRepository(get()) }
}

var utilsModule = module {

    // Utils
    single { GenericUtilsImpl() }
    single { ImageFetcherUtilImpl() }
    single { NumberFormatterUtilImpl() }
}

var networkModule = module {

    // Retrofit
    single { RetrofitFactory() }
}