package com.commit451.coiltransformations

import android.graphics.Bitmap
import android.graphics.Paint
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.createBitmap
import coil.size.Size
import coil.transform.Transformation
import com.commit451.coiltransformations.Util.safeConfig
import com.google.android.renderscript.Toolkit

/**
 * A [Transformation] that applies a Gaussian blur to an image.
 *
 * @param radius The radius of the blur.
 * @param sampling The sampling multiplier used to scale the image. Values > 1
 *  will downscale the image. Values between 0 and 1 will upscale the image.
 */
data class BlurTransformation @JvmOverloads constructor(
    private val radius: Int = DEFAULT_RADIUS,
    private val sampling: Float = DEFAULT_SAMPLING
) : Transformation {

    init {
        require(radius in 1..25) { "radius must be in [1, 25]." }
        require(sampling > 0) { "sampling must be > 0." }
    }

    override val cacheKey = "${BlurTransformation::class.java.name}-$radius-$sampling"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)

        val scaledWidth = (input.width / sampling).toInt()
        val scaledHeight = (input.height / sampling).toInt()
        val output = createBitmap(scaledWidth, scaledHeight, input.safeConfig)
        output.applyCanvas {
            scale(1 / sampling, 1 / sampling)
            drawBitmap(input, 0f, 0f, paint)
        }

        return Toolkit.blur(output, radius)
    }

    private companion object {
        private const val DEFAULT_RADIUS = 10
        private const val DEFAULT_SAMPLING = 1f
    }
}
