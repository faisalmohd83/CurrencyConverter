package com.fm.excahnge.util

import android.content.Context
import android.widget.ImageView
import com.fm.exchange.R
import com.pixplicity.sharp.Sharp
import okhttp3.*
import java.io.IOException

/**
 *
 */
object ImageUtil {

    private var httpClient: OkHttpClient? = null

    /**
     * Method to load the .svg image from remote i
     */
    fun fetchSvgRemote(context: Context, url: String, target: ImageView) {

        if (httpClient == null) {
            // Use cache for performance and basic offline capability
            httpClient = OkHttpClient.Builder()
                .cache(Cache(context.cacheDir, 5 * 1024 * 1014))
                .build()
        }

        val request = Request.Builder().url(url).build()
        httpClient!!.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                target.setImageResource(R.drawable.ic_placeholders)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val stream = response.body()!!.byteStream()
                Sharp.loadInputStream(stream).into(target)
                stream.close()
            }
        })
    }
}