package com.commit451.coiltransformations.gpu

import android.content.Context
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSepiaToneFilter

/**
 * Applies a simple sepia effect.
 * @param context context
 * @param intensity The intensity of the sepia effect, with a default of 1.0.
 */
class SepiaFilterTransformation(
    context: Context,
    private val intensity: Float = 1.0f
) : GPUFilterTransformation(context) {

    override val cacheKey: String = "${SepiaFilterTransformation::class.java.name}-$intensity"

    override fun createFilter(): GPUImageFilter = GPUImageSepiaToneFilter()
        .apply {
            setIntensity(intensity)
        }
}
