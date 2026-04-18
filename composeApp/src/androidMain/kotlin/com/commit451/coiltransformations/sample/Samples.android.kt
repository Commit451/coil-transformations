package com.commit451.coiltransformations.sample

import android.app.Application
import android.util.LruCache
import com.commit451.coiltransformations.BlurTransformation
import com.commit451.coiltransformations.facedetection.CenterOnFaceTransformation
import com.commit451.coiltransformations.gpu.BrightnessFilterTransformation
import com.commit451.coiltransformations.gpu.ContrastFilterTransformation
import com.commit451.coiltransformations.gpu.InvertFilterTransformation
import com.commit451.coiltransformations.gpu.KuwaharaFilterTransformation
import com.commit451.coiltransformations.gpu.PixelationFilterTransformation
import com.commit451.coiltransformations.gpu.SepiaFilterTransformation
import com.commit451.coiltransformations.gpu.SketchFilterTransformation
import com.commit451.coiltransformations.gpu.SwirlFilterTransformation
import com.commit451.coiltransformations.gpu.ToonFilterTransformation
import com.commit451.coiltransformations.gpu.VignetteFilterTransformation

private const val FACE_URL = "https://picsum.photos/id/64/640/640"

internal var sampleApplication: Application? = null

actual fun platformSamples(demoUrl: String): List<Sample> {
    val context = sampleApplication ?: return emptyList()
    val faceCache = LruCache<String, String>(4 * 1024 * 1024)
    return listOf(
        Sample("Blur", demoUrl, listOf(BlurTransformation(context))),
        Sample("Brightness", demoUrl, listOf(BrightnessFilterTransformation(context, 0.5f))),
        Sample("Contrast", demoUrl, listOf(ContrastFilterTransformation(context, 2.0f))),
        Sample("Invert", demoUrl, listOf(InvertFilterTransformation(context))),
        Sample("Kuwahara", demoUrl, listOf(KuwaharaFilterTransformation(context))),
        Sample("Pixelation", demoUrl, listOf(PixelationFilterTransformation(context, 20f))),
        Sample("Sepia", demoUrl, listOf(SepiaFilterTransformation(context))),
        Sample("Sketch", demoUrl, listOf(SketchFilterTransformation(context))),
        Sample("Swirl", demoUrl, listOf(SwirlFilterTransformation(context))),
        Sample("Toon", demoUrl, listOf(ToonFilterTransformation(context))),
        Sample("Vignette", demoUrl, listOf(VignetteFilterTransformation(context))),
        Sample("Face (zoom 20)", FACE_URL, listOf(CenterOnFaceTransformation(faceCache, 20))),
        Sample("Face (zoom 60)", FACE_URL, listOf(CenterOnFaceTransformation(faceCache, 60))),
        Sample("Face (zoom 100)", FACE_URL, listOf(CenterOnFaceTransformation(faceCache, 100))),
    )
}
