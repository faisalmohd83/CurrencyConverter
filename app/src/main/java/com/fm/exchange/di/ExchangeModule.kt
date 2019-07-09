package com.fm.exchange.di

import com.fm.excahnge.util.GenericUtilsImpl
import com.fm.excahnge.util.image.ImageFetcherUtilImpl
import com.fm.excahnge.util.number.NumberFormatterUtilImpl
import com.fm.exchange.network.ExchangeRepository
import com.fm.exchange.view.ExchangeAdapter
import com.fm.exchange.view.ExchangeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var appLevelModule = module {

    // ViewModel
    viewModel { ExchangeViewModel() }

    // Adapter
    single { ExchangeAdapter(get()) }

    // Repository
    single { ExchangeRepository(get()) }

    // Utils
    single { GenericUtilsImpl() }
    single { ImageFetcherUtilImpl() }
    single { NumberFormatterUtilImpl() }
}