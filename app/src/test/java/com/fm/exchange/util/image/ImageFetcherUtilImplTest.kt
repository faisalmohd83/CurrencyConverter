package com.fm.exchange.util.image

import com.fm.exchange.di.utilsModule
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.koin.test.KoinTest

class ImageFetcherUtilImplTest : KoinTest {

    private val imageFetcherUtil: ImageFetcherUtil by inject()

    @Before
    fun setUp() {
        startKoin { modules(utilsModule) }
    }

    fun `sdf`() {
//        val result = imageFetcherUtil.fetchRemoteSVGImage("" , )
//        Assert.assertNotNull(result, 18.00/*, 1.0*/)
    }

}