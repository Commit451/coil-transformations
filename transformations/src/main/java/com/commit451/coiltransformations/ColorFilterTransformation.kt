package com.commit451.coiltransformations

import android.graphics.*
import androidx.annotation.ColorInt
import androidx.core.graphics.createBitmap
import coil3.size.Size
import coil3.transform.Transformation
import com.commit451.coiltransformations.Util.safeConfig

/**
 * A [Transformation] that applies a color filter to an image.
 * @param color the color filter to apply. Typically, you would want this to have an alpha component
 */
class ColorFilterTransformation(
    @ColorInt private val color: Int
) : Transformation() {

    override val cacheKey: String = "${ColorFilterTransformation::class.java.name}-$color"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val output = createBitmap(input.width, input.height, input.safeConfig)

        val canvas = Canvas(output)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        canvas.drawBitmap(input, 0f, 0f, paint)

        return output
    }
}
