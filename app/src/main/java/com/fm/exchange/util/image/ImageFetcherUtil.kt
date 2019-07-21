package com.fm.exchange.util.image

import android.widget.ImageView

/**
 *
 */
interface ImageFetcherUtil {

    fun fetchRemoteSVGImage(url: String, imageView: ImageView)

}