package com.commit451.coiltransformations.gpu

import android.content.Context
import jp.co.cyberagent.android.gpuimage.filter.GPUImageColorInvertFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter

/**
 * Invert all the colors in the image.
 * @param context context
 */
class InvertFilterTransformation(
    context: Context
) : GPUFilterTransformation(context) {

    override fun key(): String = InvertFilterTransformation::class.java.name

    override fun createFilter(): GPUImageFilter = GPUImageColorInvertFilter()
}
