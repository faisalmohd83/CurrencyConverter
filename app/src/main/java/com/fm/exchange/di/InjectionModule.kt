package com.fm.exchange.di

import com.fm.excahnge.util.GenericUtilsImpl
import com.fm.excahnge.util.image.ImageFetcherUtilImpl
import com.fm.excahnge.util.number.NumberFormatterUtilImpl
import com.fm.exchange.common.RetrofitFactory
import com.fm.exchange.network.ExchangeRepository
import com.fm.exchange.view.ExchangeActivity
import com.fm.exchange.view.ExchangeAdapter
import com.fm.exchange.view.ExchangeViewModel
import com.fm.exchange.view.OnListItemTapListener
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var diInjectionModule = module {

    // Adapter
    single { ExchangeAdapter(ExchangeActivity() as OnListItemTapListener) }

    // ViewModel
    viewModel { ExchangeViewModel() }

    // Repository
    single { ExchangeRepository(get()) }

    // Utils
    single { GenericUtilsImpl() }
    single { ImageFetcherUtilImpl() }
    single { NumberFormatterUtilImpl() }

    // Retrofit
    single { RetrofitFactory() }
}