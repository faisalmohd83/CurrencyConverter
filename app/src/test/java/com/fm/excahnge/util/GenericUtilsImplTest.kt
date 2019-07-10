package com.fm.excahnge.util

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.koin.dsl.module
import org.koin.test.KoinTest

class GenericUtilsImplTest : KoinTest {

    private val utilsImpl: GenericUtilsImpl by inject()

    @Before
    fun setUp() {
//        startKoin(module { single { diInjectionModule } })
    }

    @Test
    fun `does getCurrencyObject() results currency object always?`() {
        val result = utilsImpl.getCurrencyObject("EUR", 1.0)
        Assert.assertNotNull(result)
    }
}