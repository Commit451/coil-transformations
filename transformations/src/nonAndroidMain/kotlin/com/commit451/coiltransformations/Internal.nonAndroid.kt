package com.commit451.coiltransformations

import coil3.Bitmap
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.ColorFilter
import org.jetbrains.skia.ColorMatrix
import org.jetbrains.skia.ImageInfo
import org.jetbrains.skia.Matrix33
import org.jetbrains.skia.Paint
import org.jetbrains.skia.Rect
import org.jetbrains.skia.SamplingMode

internal actual val Bitmap.widthPx: Int get() = width
internal actual val Bitmap.heightPx: Int get() = height

private fun Bitmap.toImage() = org.jetbrains.skia.Image.makeFromBitmap(this)

private fun newBitmap(width: Int, height: Int, source: Bitmap): Bitmap {
    val info = ImageInfo(source.imageInfo.colorInfo, width, height)
    return Bitmap().apply { allocPixels(info) }
}

internal actual fun Bitmap.centerCrop(width: Int, height: Int): Bitmap {
    if (this.width == width && this.height == height) return this

    val scale: Float
    val dx: Float
    val dy: Float
    if (this.width * height > width * this.height) {
        scale = height.toFloat() / this.height.toFloat()
        dx = (width - this.width * scale) * 0.5f
        dy = 0f
    } else {
        scale = width.toFloat() / this.width.toFloat()
        dx = 0f
        dy = (height - this.height * scale) * 0.5f
    }

    val output = newBitmap(width, height, this)
    val canvas = Canvas(output)
    val matrix = Matrix33.makeTranslate((dx + 0.5f).toInt().toFloat(), (dy + 0.5f).toInt().toFloat())
        .makeConcat(Matrix33.makeScale(scale, scale))
    canvas.setMatrix(matrix)
    canvas.drawImage(toImage(), 0f, 0f, Paint())
    return output
}

internal actual fun Bitmap.applyCrop(
    width: Int,
    height: Int,
    left: Float,
    top: Float,
    scaledWidth: Float,
    scaledHeight: Float,
): Bitmap {
    val output = newBitmap(width, height, this)
    val canvas = Canvas(output)
    val dst = Rect.makeXYWH(left, top, scaledWidth, scaledHeight)
    canvas.drawImageRect(
        toImage(),
        Rect.makeWH(this.width.toFloat(), this.height.toFloat()),
        dst,
        SamplingMode.DEFAULT,
        Paint(),
        strict = true,
    )
    return output
}

internal actual fun Bitmap.applyColorFilter(color: Int): Bitmap {
    val output = newBitmap(width, height, this)
    val canvas = Canvas(output)
    val paint = Paint().apply {
        colorFilter = ColorFilter.makeBlend(color, org.jetbrains.skia.BlendMode.SRC_ATOP)
    }
    canvas.drawImage(toImage(), 0f, 0f, paint)
    return output
}

internal actual fun Bitmap.applyGrayscale(): Bitmap {
    val output = newBitmap(width, height, this)
    val canvas = Canvas(output)
    val paint = Paint().apply {
        colorFilter = ColorFilter.makeMatrix(grayscaleMatrix)
    }
    canvas.drawImage(toImage(), 0f, 0f, paint)
    return output
}

private val grayscaleMatrix = ColorMatrix(
    0.2126f, 0.7152f, 0.0722f, 0f, 0f,
    0.2126f, 0.7152f, 0.0722f, 0f, 0f,
    0.2126f, 0.7152f, 0.0722f, 0f, 0f,
    0f, 0f, 0f, 1f, 0f,
)
