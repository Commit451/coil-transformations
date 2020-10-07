package com.commit451.coiltransformations

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.applyCanvas
import coil.bitmap.BitmapPool
import coil.size.Size
import coil.transform.Transformation

/**
 * Mask transformation using another drawable.
 * @param context context for loading the drawable
 * @param maskDrawableRes the drawable resource to use as the mask
 */
class MaskTransformation(
    private val context: Context,
    @DrawableRes val maskDrawableRes: Int
) : Transformation {

    companion object {
        private val paint = Paint()
            .apply {
                xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            }
    }

    override fun key(): String = "${MaskTransformation::class.java.name}-$maskDrawableRes"

    override suspend fun transform(pool: BitmapPool, input: Bitmap, size: Size): Bitmap {
        val width = input.width
        val height = input.height

        val output = pool.get(width, height, input.config)
        output.setHasAlpha(true)

        val mask = getMaskDrawable(context.applicationContext, maskDrawableRes)

        output.applyCanvas {
            mask.setBounds(0, 0, width, height)
            mask.draw(this)
            drawBitmap(input, 0f, 0f, paint)
        }
        pool.put(input)
        return output
    }

    private fun getMaskDrawable(context: Context, maskId: Int): Drawable {
        return ResourcesCompat.getDrawable(context.resources, maskId, null)
            ?: throw IllegalArgumentException("maskId is invalid")
    }
}
