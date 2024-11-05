package com.commit451.coiltransformations

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.createBitmap
import coil3.size.Size
import coil3.transform.Transformation
import com.commit451.coiltransformations.Util.safeConfig
import kotlin.math.max

/**
 * Mask transformation using another drawable.
 * @param context context for loading the drawable
 * @param maskDrawableRes the drawable resource to use as the mask
 */
class MaskTransformation(private val context: Context, @DrawableRes val maskDrawableRes: Int) : Transformation() {

    companion object {
        private val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG).apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        }
    }

    override val cacheKey: String = "${MaskTransformation::class.java.name}-$maskDrawableRes"

    override suspend fun transform(input: Bitmap, size: Size) = getMaskDrawable(context, maskDrawableRes).let {
        it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
        createBitmap(it.intrinsicWidth, it.intrinsicHeight, input.safeConfig).applyCanvas {
            it.draw(this)
            val scale = max(it.intrinsicWidth.toFloat() / input.width, it.intrinsicHeight.toFloat() / input.height)
            val scaledWidth = scale * input.width
            val scaledHeight = scale * input.height
            val left = (it.intrinsicWidth - scaledWidth) / 2
            val top = (it.intrinsicHeight - scaledHeight) / 2
            drawBitmap(input, null, RectF(left, top, left + scaledWidth, top + scaledHeight), paint)
        }
    }

    private fun getMaskDrawable(context: Context, maskId: Int): Drawable {
        return ResourcesCompat.getDrawable(context.resources, maskId, null)
            ?: throw IllegalArgumentException("maskId is invalid")
    }

    override fun equals(other: Any?) = (other as? MaskTransformation)?.maskDrawableRes == maskDrawableRes
    override fun hashCode() = cacheKey.hashCode()
}
