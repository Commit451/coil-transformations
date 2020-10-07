package com.commit451.coiltransformations

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import coil.bitmap.BitmapPool

/**
 * Internal utils used by various transformations
 */
internal object Util {

    private val DEFAULT_PAINT = Paint(Paint.DITHER_FLAG or Paint.FILTER_BITMAP_FLAG)

    /**
     * (Taken from Glide)
     * A potentially expensive operation to crop the given Bitmap so that it fills the given
     * dimensions. This operation is significantly less expensive in terms of memory if a mutable
     * Bitmap with the given dimensions is passed in as well.
     *
     * @param pool The BitmapPool to obtain a bitmap from.
     * @param inBitmap The Bitmap to resize.
     * @param width The width in pixels of the final Bitmap.
     * @param height The height in pixels of the final Bitmap.
     * @return The resized Bitmap (will be recycled if recycled is not null).
     */
    fun centerCrop(pool: BitmapPool, inBitmap: Bitmap, width: Int, height: Int): Bitmap {
        if (inBitmap.width == width && inBitmap.height == height) {
            return inBitmap
        }
        // From ImageView/Bitmap.createScaledBitmap.
        val scale: Float
        val dx: Float
        val dy: Float
        val m = Matrix()
        if (inBitmap.width * height > width * inBitmap.height) {
            scale = height.toFloat() / inBitmap.height.toFloat()
            dx = (width - inBitmap.width * scale) * 0.5f
            dy = 0f
        } else {
            scale = width.toFloat() / inBitmap.width.toFloat()
            dx = 0f
            dy = (height - inBitmap.height * scale) * 0.5f
        }

        m.setScale(scale, scale)
        m.postTranslate((dx + 0.5f).toInt().toFloat(), (dy + 0.5f).toInt().toFloat())

        val result = pool.get(width, height, inBitmap.config)
        // We don't add or remove alpha, so keep the alpha setting of the Bitmap we were given.
        setAlpha(inBitmap, result)

        applyMatrix(inBitmap, result, m)
        return result
    }

    /**
     * Sets the alpha of the Bitmap we're going to re-use to the alpha of the Bitmap we're going to
     * transform. This keeps [android.graphics.Bitmap.hasAlpha]} consistent before and after
     * the transformation for transformations that don't add or remove transparent pixels.
     *
     * @param inBitmap The [android.graphics.Bitmap] that will be transformed.
     * @param outBitmap The [android.graphics.Bitmap] that will be returned from the transformation.
     */
    private fun setAlpha(inBitmap: Bitmap, outBitmap: Bitmap) {
        outBitmap.setHasAlpha(inBitmap.hasAlpha())
    }

    private fun applyMatrix(inBitmap: Bitmap, targetBitmap: Bitmap, matrix: Matrix) {
        try {
            val canvas = Canvas(targetBitmap)
            canvas.drawBitmap(inBitmap, matrix, DEFAULT_PAINT)
            clear(canvas)
        } finally {
        }
    }

    // Avoids warnings in M+.
    private fun clear(canvas: Canvas) {
        canvas.setBitmap(null)
    }
}
