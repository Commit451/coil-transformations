package com.commit451.coiltransformations

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import coil.bitmappool.BitmapPool
import coil.transform.Transformation
import kotlin.math.max

/**
 * A transformation that crops to a certain part of the image
 * @param cropType the type of crop to apply. Default is [CropType.CENTER]
 */
class CropTransformation(
    private val cropType: CropType = CropType.CENTER
) : Transformation {

    enum class CropType {
        TOP,
        CENTER,
        BOTTOM
    }

    override fun key(): String = "${CropTransformation::class.java.name}-$cropType"

    override suspend fun transform(pool: BitmapPool, input: Bitmap): Bitmap {
        val width = input.width
        val height = input.height

        val config = if (input.config != null) input.config else Bitmap.Config.ARGB_8888
        val output = pool.get(width, height, config)

        output.setHasAlpha(true)

        val scaleX = width.toFloat() / input.width
        val scaleY = height.toFloat() / input.height
        val scale = max(scaleX, scaleY)

        val scaledWidth = scale * input.width
        val scaledHeight = scale * input.height
        val left = (width - scaledWidth) / 2
        val top = getTop(height.toFloat(), scaledHeight)
        val targetRect = RectF(left, top, left + scaledWidth, top + scaledHeight)

        val canvas = Canvas(output)
        canvas.drawBitmap(input, null, targetRect, null)

        pool.put(input)
        return output
    }

    private fun getTop(height: Float, scaledHeight: Float): Float {
        return when (cropType) {
            CropType.TOP -> 0f
            CropType.CENTER -> (height - scaledHeight) / 2
            CropType.BOTTOM -> height - scaledHeight
        }
    }
}
