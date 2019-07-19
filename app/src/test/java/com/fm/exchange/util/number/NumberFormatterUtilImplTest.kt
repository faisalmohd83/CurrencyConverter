package com.fm.exchange.util.number

import android.util.Log
import com.fm.exchange.di.utilsModule
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class NumberFormatterUtilImplTest : KoinTest {

    private val numberFormatterUtil: NumberFormatterUtilImpl by inject()

    @Before
    fun setUp() {
        startKoin { modules(utilsModule) }
    }

    @Test
    fun `does formatter returns two digit faction if supplied one digit value`() {
        val result = numberFormatterUtil.getAdjustedCurrencyRate(18.0)
        Log.d("", "Test 01 : $result")
        Assert.assertEquals(result, 18.07, 0.1)
    }

    @Test
    fun `does formatter returns two digit faction if supplied multiple digits value`() {
        val result = numberFormatterUtil.getAdjustedCurrencyRate(18.12343)
        Log.d("", "Test 02 : $result")

        Assert.assertEquals(result, 18.12, 1.0)
    }

    @Test
    fun `does formatter returns rounded two digit faction if supplied multiple digits value`() {
        val result = numberFormatterUtil.getAdjustedCurrencyRate(18.12876)
        Log.d("", "Test 03 : $result")

        Assert.assertEquals(result, 18.10, 1.0)
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}