package com.commit451.coiltransformations.gpu

import android.content.Context
import jp.co.cyberagent.android.gpuimage.filter.GPUImageContrastFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter

/**
 * Adjust the contrast of an image.
 * @param context context
 * @param contrast contrast value ranges from 0.0 to 4.0, with 1.0 as the normal level
 */
class ContrastFilterTransformation(
    context: Context,
    private val contrast: Float = 1.0f
) : GPUFilterTransformation(context) {

    init {
        require(contrast >= 0) { "Contrast must be >= 0.0" }
        require(contrast <= 4.0) { "Contrast must be <= 4.0" }
    }

    override fun key(): String = "${ContrastFilterTransformation::class.java.name}-$contrast"

    override fun createFilter(): GPUImageFilter = GPUImageContrastFilter()
        .apply {
            setContrast(contrast)
        }
}
