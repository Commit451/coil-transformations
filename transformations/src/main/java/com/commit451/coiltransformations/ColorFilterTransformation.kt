package com.commit451.coiltransformations

import android.graphics.*
import androidx.annotation.ColorInt
import coil.bitmappool.BitmapPool
import coil.size.Size
import coil.transform.Transformation

/**
 * A [Transformation] that applies a color filter to an image.
 * @param color the color filter to apply. Typically, you would want this to have an alpha component
 */
class ColorFilterTransformation(
    @ColorInt private val color: Int
) : Transformation {

    override fun key(): String = "${ColorFilterTransformation::class.java.name}-$color"

    override suspend fun transform(pool: BitmapPool, input: Bitmap, size: Size): Bitmap {
        val width = input.width
        val height = input.height

        val config = input.config
        val output = pool.get(width, height, config)

        val canvas = Canvas(output)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        canvas.drawBitmap(input, 0f, 0f, paint)

        return output
    }
}
