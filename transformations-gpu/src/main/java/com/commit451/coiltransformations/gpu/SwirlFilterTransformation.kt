package com.commit451.coiltransformations.gpu

import android.content.Context
import android.graphics.PointF
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSwirlFilter

/**
 * Creates a swirl distortion on the image.
 * @param context context
 * @param radius the radius of the swirl, default 0.5f
 * @param angle the angle of the swirl, default 1.0f
 * @param center the center of the swirl, default PointF(0.5f, 0.5f)
 */
class SwirlFilterTransformation(
    context: Context,
    private val radius: Float = 0.5f,
    private val angle: Float = 1.0f,
    private val center: PointF = PointF(0.5f, 0.5f)
) : GPUFilterTransformation(context) {

    override fun key(): String = "${SwirlFilterTransformation::class.java.name}-$radius-$angle-$center"

    override fun createFilter(): GPUImageFilter = GPUImageSwirlFilter()
        .apply {
            setRadius(radius)
            setAngle(angle)
            setCenter(center)
        }
}
