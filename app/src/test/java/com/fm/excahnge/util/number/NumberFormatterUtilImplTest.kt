package com.fm.excahnge.util.number

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.KoinApplication
import org.koin.core.inject
import org.koin.test.KoinTest

class NumberFormatterUtilImplTest : KoinTest {

    private val numberFormatterUtil: NumberFormatterUtil by inject()

    @Before
    fun setUp() {
//         startKoin(module { single { appLevelModule } })
        KoinApplication.create()
    }

    @Test
    fun `does formatter returns two digit faction if supplied one digit value`() {
        val result = numberFormatterUtil.getAdjustedCurrencyRate(18.0)
        Assert.assertEquals(result, 18.00/*, 1.0*/)
    }

    @Test
    fun `does formatter returns two digit faction if supplied multiple digits value`() {
        val result = numberFormatterUtil.getAdjustedCurrencyRate(18.12343)
        Assert.assertEquals(result, 18.12/*, 1.0*/)
    }

    @Test
    fun `does formatter returns rounded two digit faction if supplied multiple digits value`() {
        val result = numberFormatterUtil.getAdjustedCurrencyRate(18.12876)
        Assert.assertEquals(result, 18.13/*, 1.0*/)
    }

}