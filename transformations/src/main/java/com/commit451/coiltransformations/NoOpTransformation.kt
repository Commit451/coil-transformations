package com.commit451.coiltransformations

import android.graphics.Bitmap
import coil.bitmap.BitmapPool
import coil.size.Size
import coil.transform.Transformation

class NoOpTransformation : Transformation {
    override fun key(): String {
        return NoOpTransformation::class.java.name
    }

    override suspend fun transform(pool: BitmapPool, input: Bitmap, size: Size): Bitmap {
        return input
    }
}
