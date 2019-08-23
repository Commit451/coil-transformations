package com.commit451.coiltransformations.gpu

import android.content.Context
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImagePixelationFilter

/**
 * Applies a Pixelation effect to the image.
 * @param context context
 * @param pixel Amount of pixelation to apply with a default of 10.0.
 */
class PixelationFilterTransformation(
    context: Context,
    private val pixel: Float = 10.0f
) : GPUFilterTransformation(context) {

    override fun key(): String = "${PixelationFilterTransformation::class.java.name}-$pixel"

    override fun createFilter(): GPUImageFilter = GPUImagePixelationFilter()
        .apply {
            setPixel(pixel)
        }
}
