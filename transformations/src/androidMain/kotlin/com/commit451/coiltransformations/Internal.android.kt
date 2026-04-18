package com.commit451.coiltransformations

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.RectF
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.createBitmap

internal actual val Bitmap.widthPx: Int get() = width
internal actual val Bitmap.heightPx: Int get() = height

private val Bitmap.safeConfig: Bitmap.Config
    get() = config ?: Bitmap.Config.ARGB_8888

private val DEFAULT_PAINT = Paint(Paint.DITHER_FLAG or Paint.FILTER_BITMAP_FLAG)

internal actual fun Bitmap.centerCrop(width: Int, height: Int): Bitmap {
    if (this.width == width && this.height == height) return this

    val scale: Float
    val dx: Float
    val dy: Float
    val m = Matrix()
    if (this.width * height > width * this.height) {
        scale = height.toFloat() / this.height.toFloat()
        dx = (width - this.width * scale) * 0.5f
        dy = 0f
    } else {
        scale = width.toFloat() / this.width.toFloat()
        dx = 0f
        dy = (height - this.height * scale) * 0.5f
    }

    m.setScale(scale, scale)
    m.postTranslate((dx + 0.5f).toInt().toFloat(), (dy + 0.5f).toInt().toFloat())

    val result = createBitmap(width, height, safeConfig)
    result.setHasAlpha(hasAlpha())
    Canvas(result).drawBitmap(this, m, DEFAULT_PAINT)
    return result
}

internal actual fun Bitmap.applyCrop(
    width: Int,
    height: Int,
    left: Float,
    top: Float,
    scaledWidth: Float,
    scaledHeight: Float,
): Bitmap {
    val output = createBitmap(width, height, safeConfig)
    output.setHasAlpha(true)
    Canvas(output).drawBitmap(this, null, RectF(left, top, left + scaledWidth, top + scaledHeight), null)
    return output
}

internal actual fun Bitmap.applyColorFilter(color: Int): Bitmap {
    val output = createBitmap(width, height, safeConfig)
    val paint = Paint().apply {
        isAntiAlias = true
        colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }
    Canvas(output).drawBitmap(this, 0f, 0f, paint)
    return output
}

private val GRAYSCALE_FILTER = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })

internal actual fun Bitmap.applyGrayscale(): Bitmap {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG).apply {
        colorFilter = GRAYSCALE_FILTER
    }
    val output = createBitmap(width, height, safeConfig)
    output.applyCanvas {
        drawBitmap(this@applyGrayscale, 0f, 0f, paint)
    }
    return output
}
