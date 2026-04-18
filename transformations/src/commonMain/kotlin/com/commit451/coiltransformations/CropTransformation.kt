package com.commit451.coiltransformations

import coil3.Bitmap
import coil3.size.Size
import coil3.size.pxOrElse
import coil3.transform.Transformation
import kotlin.math.max

/**
 * A transformation that crops to a certain part of the image.
 * @param cropType the type of crop to apply. Default is [CropType.CENTER]
 */
class CropTransformation(
    private val cropType: CropType = CropType.CENTER,
) : Transformation() {

    enum class CropType {
        TOP,
        CENTER,
        BOTTOM,
    }

    override val cacheKey: String = "CropTransformation-$cropType"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val width = size.width.pxOrElse { input.widthPx }
        val height = size.height.pxOrElse { input.heightPx }

        val scaleX = width.toFloat() / input.widthPx
        val scaleY = height.toFloat() / input.heightPx
        val scale = max(scaleX, scaleY)

        val scaledWidth = scale * input.widthPx
        val scaledHeight = scale * input.heightPx
        val left = (width - scaledWidth) / 2
        val top = when (cropType) {
            CropType.TOP -> 0f
            CropType.CENTER -> (height - scaledHeight) / 2
            CropType.BOTTOM -> height - scaledHeight
        }

        return input.applyCrop(width, height, left, top, scaledWidth, scaledHeight)
    }
}
