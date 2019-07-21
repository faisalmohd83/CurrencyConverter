package com.fm.exchange.util.image

import android.content.Context
import android.widget.ImageView
import com.fm.exchange.R
import com.pixplicity.sharp.Sharp
import okhttp3.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.IOException

/**
 * Helper class to fetch SVG images from remote efficiently
 */
class ImageFetcherUtilImpl : ImageFetcherUtil, KoinComponent {

    private val mContext: Context by inject()

    private var httpClient: OkHttpClient? = null

    /**
     * Method to load the SVG image from remote
     */
    override fun fetchRemoteSVGImage(url: String, imageView: ImageView) {

        // TODO: do async
        if (httpClient == null) {
            // Use cache for performance and basic offline capability
            httpClient = OkHttpClient.Builder()
                .cache(Cache(mContext.cacheDir, 5 * 1024 * 1014))
                .build()
        }

        val request = Request.Builder().url(url).build()
        httpClient!!.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                imageView.setImageResource(R.drawable.ic_placeholders)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val stream = response.body!!.byteStream()
                Sharp.loadInputStream(stream).into(imageView)
                stream.close()
            }
        })
    }
}