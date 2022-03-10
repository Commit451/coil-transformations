package com.commit451.coiltransformations.gpu

import android.content.Context
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter

/**
 * Filter to adjust the brightness of the image
 * @param context context
 * @param brightness value ranges from -1.0 to 1.0, with 0.0 as the normal level
 */
class BrightnessFilterTransformation(
    context: Context,
    private val brightness: Float = 0.0f
) : GPUFilterTransformation(context) {

    override val cacheKey: String = "${BrightnessFilterTransformation::class.java.name}-$brightness"

    override fun createFilter(): GPUImageFilter = GPUImageBrightnessFilter()
        .apply {
            setBrightness(brightness)
        }
}
