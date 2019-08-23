package com.commit451.coiltransformations.gpu

import android.content.Context
import android.graphics.PointF
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageVignetteFilter

/**
 * Performs a vignetting effect, fading out the image at the edges.
 * @param context context,
 * @param center the center to apply the vignetting, default PointF(0.5f, 0.5f)
 * @param color the color of the vignette, default floatArrayOf(0.0f, 0.0f, 0.0f)
 * @param start the start of the vignette, default 0.0f,
 * @param end the end of the vignette, default 0.75f
 */
class VignetteFilterTransformation(
    context: Context,
    private val center: PointF = PointF(0.5f, 0.5f),
    private val color: FloatArray = floatArrayOf(0.0f, 0.0f, 0.0f),
    private val start: Float = 0.0f,
    private val end: Float = 0.75f
) : GPUFilterTransformation(context) {

    override fun key(): String = "${VignetteFilterTransformation::class.java.name}-$center-$color-$start-$end"

    override fun createFilter(): GPUImageFilter = GPUImageVignetteFilter()
        .apply {
            setVignetteCenter(center)
            setVignetteColor(color)
            setVignetteStart(start)
            setVignetteEnd(end)
        }
}
