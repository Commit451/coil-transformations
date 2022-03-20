package com.commit451.coiltransformations.gpu

import android.content.Context
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageKuwaharaFilter

/**
 * Apply a [Kuwahara filter](https://en.wikipedia.org/wiki/Kuwahara_filter) to all the colors in the image.
 *
 * @param context context
 * @param radius The radius to sample from when creating the brush-stroke effect, with a default of 25. The larger the radius, the slower the filter.
 */
class KuwaharaFilterTransformation(
    context: Context,
    private val radius: Int = 25
) : GPUFilterTransformation(context) {

    override val cacheKey: String = "${KuwaharaFilterTransformation::class.java.name}-$radius"

    override fun createFilter(): GPUImageFilter = GPUImageKuwaharaFilter()
        .apply {
            setRadius(radius)
        }
}
