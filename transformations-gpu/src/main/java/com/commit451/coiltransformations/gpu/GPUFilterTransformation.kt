package com.commit451.coiltransformations.gpu

import android.content.Context
import android.graphics.Bitmap
import coil.bitmap.BitmapPool
import coil.size.Size
import coil.transform.Transformation
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter

/**
 * Base for GPU filter transformations. Typically you do not need to worry about this class, but can
 * use it to easily create your own filter transformations using the [createFilter] method.
 * @param context context
 */
abstract class GPUFilterTransformation(
    private val context: Context
) : Transformation {

    /**
     * Create the [GPUImageFilter] to apply to this [Transformation]
     */
    abstract fun createFilter(): GPUImageFilter

    override suspend fun transform(pool: BitmapPool, input: Bitmap, size: Size): Bitmap {
        val gpuImage = GPUImage(context)
        gpuImage.setImage(input)
        gpuImage.setFilter(createFilter())
        return gpuImage.bitmapWithFilterApplied
    }
}
