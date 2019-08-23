package com.commit451.coiltransformations.gpu

import android.content.Context
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSketchFilter

/**
 * Applies a sketch filter to the image
 * @param context context
 */
class SketchFilterTransformation(
    context: Context
) : GPUFilterTransformation(context) {

    override fun key(): String = SketchFilterTransformation::class.java.name

    override fun createFilter(): GPUImageFilter = GPUImageSketchFilter()
}
