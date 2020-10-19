package com.commit451.coiltransformations.facedetection

import android.graphics.Bitmap
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

/**
 * Finds the largest face in an image, and center crops around it.
 * @param cache: an optional LruCache, where the key is unique for every combination of input image
 * and zoom level, and the value is the square used to center crop the image. This means the transformation
 * is still run the second time it appears on the screen, but the face detection algorithm and math to find
 * the center crop square is only run once. Caching the square coordinates is slightly slower than caching
 * the transformed image itself, but it is much more frugal with memory.
 * @param zoom: an Int between 0 and 100, where 0 (centered but not zoomed) represents the largest
 * square that centers the face in a least one dimension, and 100 (up close) represents the smallest
 * square that contains the face and centers it in a least one dimension.
 */
class CenterOnFaceTransformation constructor(
    private val cache: LruCache<String, String>? = null,
    val zoom: Int
) : Transformation {

    companion object {
        private const val TAG = "CenterOnFaceTransform"
    }

    override fun key(): String = CenterOnFaceTransformation::class.java.name + zoom.toString()

    override suspend fun transform(pool: BitmapPool, input: Bitmap, size: Size): Bitmap {
        require(zoom in 0..100)
        // High-accuracy landmark detection and face classification
        val inputByteArray = input.toByteArray()
        val inputByteArrayHashCode = inputByteArray.toList().hashCode()
        val transformationHash = (zoom.hashCode() xor inputByteArrayHashCode).toString()

        val cachedRectString = cache?.get(transformationHash)
        val cachedRect = Rect.unflattenFromString(cachedRectString)
        // Got the center cropping square from the cache, crop the image with it.
        if (cachedRect != null) {
            return frameBitmap(input, cachedRect)
        }

        // Initialize FaceDetectorOptions
        val highAccuracyOpts = FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .build()

        // Get a face detector.
        val detector = FaceDetection.getClient(highAccuracyOpts)
        // Put the input image into MLKIt's InputImage format.
        val image = InputImage.fromBitmap(input, 0)

        // Run the facial detection algorithm to detect all the faces in the image.
        return suspendCancellableCoroutine<Bitmap> { continuation ->
            detector.process(image).addOnSuccessListener { faces ->
                detector.close()
                // Find the largest detected face.
                val biggest = faces.maxByOrNull { it.boundingBox.height() }

                if (biggest != null) {
                    val boundingBox = biggest.boundingBox
                    // Calculate the best square to frame the face with the desired zoom level.
                    val square = zoomedSquareContainingFace(zoom, input, boundingBox)
                    // Use the square to crop the bitmap.
                    val output: Bitmap = frameBitmap(input, square)
                    // Add it to the cache so we don't have to do this again.
                    cache?.put(transformationHash, square.flattenToString())
                    // Return the bitmap from this continuation.
                    continuation.resume(output)
                } else {
                    // If MLKit doesn't detect any faces, just return the original input.
                    continuation.resume(input)
                }
            }.addOnFailureListener { e ->
                // Log the exception so we can figure out what's going wrong.
                Log.e(TAG, "transform: ERROR: $e")
                continuation.resumeWithException(e)
            }
        }
    }

    /**
     * @param input: the input image.
     * @param square: used to crop the image.
     * @return the cropped image as a Bitmap.
     */
    private fun frameBitmap(input: Bitmap, square: Rect): Bitmap {
        return Bitmap.createBitmap(
                input,
                square.left,
                square.top,
                square.width(),
                square.height()
        )
    }

    /**
     * Converts a Bitmap to a ByteArray.
     * @return the Bitmap as a ByteArray with zero compression.
     */
    private fun Bitmap.toByteArray(): ByteArray {
        val stream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    /**
     * @param input: The input image.
     * @param boundingBox: The rectangle containing the largest detected face in the image.
     * @return the smallest square containing the largest face, centered in at least one dimension.
     */
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
        return Rect(left, top, right, bottom
        )
    }

    /**
     * @param zoom: the zoom level, 0-100 inclusive.
     * @param input: the input image.
     * @param boundingBox: the rectangle containing the face.
     * @return the side length of the square we should make to crop the face for the given zoom.
     */
    private fun sideLengthForZoom(zoom: Int, input: Bitmap, boundingBox: Rect): Int {
        val zoomRatio = ((100 - zoom) / 100.0)
        val smallestSq = smallestSquareContainingFace(input, boundingBox)
        val largestSq = largestSquareContainingFace(input, boundingBox)

        val sideLengthDiff = largestSq.height() - smallestSq.height()
        return (smallestSq.height() + (zoomRatio * sideLengthDiff)).toInt()
    }

    /**
     * @param zoom: the zoom level, 0-100 inclusive.
     * @param input: the input image.
     * @param boundingBox: the rectangle containing the face.
     * @return the square to crop the face for the given zoom.
     */
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

    /**
     * @param input: the input image.
     * @param boundingBox: the rectangle containing the most prominent face.
     * @param sideLength: the size of the square we're going to frame the face in.
     * @return the square that will frame the face.
     */
    private fun squareOfSize(input: Bitmap, boundingBox: Rect, sideLength: Int): Rect {
        val faceCenterX = boundingBox.centerX()
        val faceCenterY = boundingBox.centerY()
        val sideLengthHalf = sideLength / 2
        val left: Int
        val right: Int
        val top: Int
        val bottom: Int

        // We can center the face horizontally.
        if (faceCenterX - sideLengthHalf >= 0 && faceCenterX + sideLengthHalf < input.width) {
            left = faceCenterX - sideLengthHalf
            right = left + sideLength
        }
        // We don't have enough room on the left to center it horizontally.
        // The left edge of the input will be the left edge of the output.
        else if (faceCenterX - sideLengthHalf < 0) {
            left = 0
            right = left + sideLength
        }
        // We don't have enough room on the right to center it horizontally.
        // The right edge of the input will be the right edge of the output.
        else {
            right = input.width
            left = right - sideLength
        }

        // We can center the face vertically.
        if (faceCenterY - sideLengthHalf >= 0 && faceCenterY + sideLengthHalf < input.height) {
            top = faceCenterY - sideLengthHalf
            bottom = top + sideLength
        }
        // We don't have enough room on the top to center it vertically.
        // The top edge of the input will be the top edge of the output.
        else if (faceCenterY - sideLengthHalf < 0) {
            top = 0
            bottom = top + sideLength
        }
        // We don't have enough room on the top to center it vertically.
        // The bottom edge of the input will be the bottom edge of the output.
        else {
            bottom = input.height
            top = bottom - sideLength
        }

        return Rect(left, top, right, bottom)
    }

    /**
     * @param input: The input image.
     * @param boundingBox: The rectangle containing the largest detected face in the image.
     * @return the largest square containing the largest face, centered in at least one dimension.
     */
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
