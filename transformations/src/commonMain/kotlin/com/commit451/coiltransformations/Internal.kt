package com.commit451.coiltransformations

import coil3.Bitmap

internal expect val Bitmap.widthPx: Int
internal expect val Bitmap.heightPx: Int

internal expect fun Bitmap.centerCrop(width: Int, height: Int): Bitmap

internal expect fun Bitmap.applyCrop(
    width: Int,
    height: Int,
    left: Float,
    top: Float,
    scaledWidth: Float,
    scaledHeight: Float,
): Bitmap

internal expect fun Bitmap.applyColorFilter(color: Int): Bitmap

internal expect fun Bitmap.applyGrayscale(): Bitmap
