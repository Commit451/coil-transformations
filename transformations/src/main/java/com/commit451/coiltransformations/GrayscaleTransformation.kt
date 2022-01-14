@file:Suppress("unused")
package com.commit451.coiltransformations

import android.graphics.Bitmap
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import androidx.core.graphics.applyCanvas
import coil.bitmap.BitmapPool
import coil.size.Size
import coil.transform.Transformation
import com.commit451.coiltransformations.Util.safeConfig

/**
 * A [Transformation] that converts an image to shades of gray.
 */
class GrayscaleTransformation : Transformation {

    override fun key(): String = GrayscaleTransformation::class.java.name

    override suspend fun transform(pool: BitmapPool, input: Bitmap, size: Size): Bitmap {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        paint.colorFilter = COLOR_FILTER

        val output = pool.get(input.width, input.height, input.safeConfig)
        output.applyCanvas {
            drawBitmap(input, 0f, 0f, paint)
        }

        return output
    }

    override fun equals(other: Any?) = other is GrayscaleTransformation

    override fun hashCode() = javaClass.hashCode()

    override fun toString() = "GrayscaleTransformation()"

    private companion object {
        val COLOR_FILTER = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })
    }
}
