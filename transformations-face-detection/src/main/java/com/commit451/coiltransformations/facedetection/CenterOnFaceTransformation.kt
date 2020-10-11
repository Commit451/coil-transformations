package com.commit451.coiltransformations.facedetection

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapRegionDecoder
import android.graphics.Rect
import android.util.Log
import android.util.LruCache
import coil.bitmap.BitmapPool
import coil.size.Size
import coil.transform.Transformation
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine

class CenterOnFaceTransformation constructor(
    private val cache: LruCache<String, String>? = null,
    val zoom: Int
) : Transformation {

    companion object {
        private const val TAG = "CenterOnFaceTransform"
    }

    override fun key(): String = CenterOnFaceTransformation::class.java.name + zoom.toString()

    override suspend fun transform(pool: BitmapPool, input: Bitmap, size: Size): Bitmap {
        // High-accuracy landmark detection and face classification
        val inputByteArray = input.toByteArray()
        val inputByteArrayHashCode = inputByteArray.toList().hashCode()
        val transformationHash = (zoom.hashCode() xor inputByteArrayHashCode).toString()

        val cachedRectString = cache?.get(transformationHash)
        val cachedRect = Rect.unflattenFromString(cachedRectString)
        if (cachedRect != null) {
            return Bitmap.createBitmap(
                    input,
                    cachedRect.left,
                    cachedRect.top,
                    cachedRect.width(),
                    cachedRect.height()
            )
        }
        val highAccuracyOpts = FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .build()

        val detector = FaceDetection.getClient(highAccuracyOpts)
        val image = InputImage.fromBitmap(input, 0)

        return suspendCancellableCoroutine<Bitmap> { continuation ->
            detector.process(image).addOnSuccessListener { faces ->
                detector.close()
                val biggest = faces.maxByOrNull { it.boundingBox.height() }

                if (biggest != null) {
                    val boundingBox = biggest.boundingBox
                    val square = zoomedSquareContainingFace(zoom, input, boundingBox)
                    val output: Bitmap = frameBitmap(inputByteArray, square)
                        cache?.put(transformationHash, square.flattenToString())
                        continuation.resume(output)
                } else {
                    continuation.resume(input)
                }
            }.addOnFailureListener { e ->
                Log.e(TAG, "transform: ERROR: $e")
                continuation.resumeWithException(e)
            }
        }
    }

    private fun frameBitmap(inputByteArray: ByteArray, square: Rect): Bitmap {
        val decoder = BitmapRegionDecoder.newInstance(
                inputByteArray,
                0,
                inputByteArray.size,
                true
        )
        val options: BitmapFactory.Options = BitmapFactory.Options()
        options.inScaled = false
        options.inJustDecodeBounds = true
        return decoder.decodeRegion(square, options)
    }

    private fun Bitmap.toByteArray(): ByteArray {
        val stream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    // Creates the smallest square that encloses the image's most prominent face.
    private fun smallestSquareContainingFace(input: Bitmap, boundingBox: Rect): Rect {
        val centerX = boundingBox.exactCenterX()
        val centerY = boundingBox.exactCenterY()

        val halfWidth = Collections.min(
                listOf(
                        centerX,
                        centerY,
                        input.width.toFloat() - centerX,
                        input.height.toFloat() - centerY
                )
        )
        val width = halfWidth * 2
        val left = (centerX - halfWidth).toInt()
        val top = (centerY - halfWidth).toInt()
        val right = left + width.toInt()
        val bottom = top + width.toInt()
        return Rect(
                left,
                top,
                right,
                bottom
        )
    }

    private fun sideLengthForZoom(zoom: Int, input: Bitmap, boundingBox: Rect): Int {
        val zoomRatio = ((100 - zoom) / 100.0)
        val smallestSq = smallestSquareContainingFace(input, boundingBox)
        val largestSq = largestSquareContainingFace(input, boundingBox)

        val sideLengthDiff = largestSq.height() - smallestSq.height()
        val sideLength = (smallestSq.height() + (zoomRatio * sideLengthDiff)).toInt()
        return sideLength
    }

    private fun zoomedSquareContainingFace(zoom: Int, input: Bitmap, boundingBox: Rect): Rect {
        require(zoom in 0..100) { "Zoom must be >= 0 and <= 100" }
        if (zoom == 0) {
            return largestSquareContainingFace(input, boundingBox)
        }
        if (zoom == 100) {
            return smallestSquareContainingFace(input, boundingBox)
        }
        val newSideLength = sideLengthForZoom(zoom, input, boundingBox)
        val square = squareOfSize(input, boundingBox, newSideLength)
        return square
    }

    private fun squareOfSize(input: Bitmap, boundingBox: Rect, sideLength: Int): Rect {
        val faceCenterX = boundingBox.centerX()
        val faceCenterY = boundingBox.centerY()
        val sideLengthHalf = sideLength / 2
        val left: Int
        val right: Int
        val top: Int
        val bottom: Int

        if (faceCenterX - sideLengthHalf >= 0 && faceCenterX + sideLengthHalf < input.width) {
            left = faceCenterX - sideLengthHalf
            right = left + sideLength
        } else if (faceCenterX - sideLengthHalf < 0) {
            left = 0
            right = left + sideLength
        } else {
            right = input.width
            left = right - sideLength
        }

        if (faceCenterY - sideLengthHalf >= 0 && faceCenterY + sideLengthHalf < input.height) {
            top = faceCenterY - sideLengthHalf
            bottom = top + sideLength
        } else if (faceCenterY - sideLengthHalf < 0) {
            top = 0
            bottom = top + sideLength
        } else {
            bottom = input.height
            top = bottom - sideLength
        }
        return Rect(left, top, right, bottom)
    }

    private fun largestSquareContainingFace(input: Bitmap, boundingBox: Rect): Rect {
        val centerX = boundingBox.exactCenterX()
        val centerY = boundingBox.exactCenterY()
        val left: Int
        val right: Int
        val top: Int
        val bottom: Int

        when {
            // clip left and/or right (height x height)
            input.width > input.height -> {
                top = 0
                bottom = input.height
                when {
                    // chop the right
                    centerX < input.height / 2 -> {
                        left = 0
                        right = left + input.height
                    }
                    // chop the left
                    input.width - centerX < input.height / 2 -> {
                        right = input.width
                        left = input.width - input.height
                    }
                    // chop left and right
                    else -> {
                        left = (centerX - (input.height / 2)).toInt()
                        right = left + input.height
                    }
                }
            }
            // clip top and/or bottom (width x width)
            input.width < input.height -> {
                left = 0
                right = input.width
                when {
                    // chop the bottom
                    centerY < input.width / 2 -> {
                        top = 0
                        bottom = input.width
                    }
                    // chop the top
                    input.height - centerY < input.width / 2 -> {
                        bottom = input.height
                        top = input.height - input.width
                    }
                    // chop top and bottom
                    else -> {
                        top = (centerY - (input.width / 2)).toInt()
                        bottom = top + input.width
                    }
                }
            }
            // do nothing if width == height (square image)
            else -> {
                top = 0
                bottom = input.height
                left = 0
                right = input.width
            }
        }

        return Rect(
                left,
                top,
                right,
                bottom
        )
    }
}
