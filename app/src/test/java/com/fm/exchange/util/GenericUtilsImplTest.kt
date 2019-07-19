package com.fm.exchange.util

import com.fm.exchange.di.utilsModule
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.inject
import org.koin.test.KoinTest

class GenericUtilsImplTest : KoinTest {

    private val genericUtils: GenericUtilsImpl by inject()

    @Before
    fun setUp() {
        startKoin { modules(utilsModule) }
    }

    @Test
    fun `does getCurrencyObject() results currency object always?`() {
        val currency = genericUtils.getCurrencyObject("GBP", 1.0)
        Assert.assertNotNull(currency)

        // --
//        Assert.assertEquals(currency.name, "British Pound Sterling")
//        Assert.assertEquals(currency.flag_url, "https://restcountries.eu/data/gbr.svg")
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}