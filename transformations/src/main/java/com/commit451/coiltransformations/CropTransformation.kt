package com.commit451.coiltransformations

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import androidx.core.graphics.createBitmap
import coil.size.Size
import coil.size.pxOrElse
import coil.transform.Transformation
import com.commit451.coiltransformations.Util.safeConfig
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

    override val cacheKey: String = "${CropTransformation::class.java.name}-$cropType"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val width = size.width.pxOrElse { input.width }
        val height = size.height.pxOrElse { input.height }

        val output = createBitmap(width, height, input.safeConfig)

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
