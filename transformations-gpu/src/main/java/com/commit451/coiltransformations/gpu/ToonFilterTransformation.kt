package com.commit451.coiltransformations.gpu

import android.content.Context
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageToonFilter

/**
 * Creates a cartoon filter effect.
 * @param context context
 * @param threshold The threshold at which to apply the edges, default of 0.2f.
 * @param quantizationLevels The levels of quantization for the posterization of colors within the scene, default of 10.0f.
 */
class ToonFilterTransformation(
    context: Context,
    private val threshold: Float = 0.2f,
    private val quantizationLevels: Float = 10.0f
) : GPUFilterTransformation(context) {

    override val cacheKey: String = "${ToonFilterTransformation::class.java.name}-$threshold-$quantizationLevels"

    override fun createFilter(): GPUImageFilter = GPUImageToonFilter()
        .apply {
            setThreshold(threshold)
            setQuantizationLevels(quantizationLevels)
        }
}
