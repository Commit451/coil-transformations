package com.commit451.coiltransformations.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.commit451.coiltransformations.ColorFilterTransformation
import com.commit451.coiltransformations.CropTransformation
import com.commit451.coiltransformations.GrayscaleTransformation
import com.commit451.coiltransformations.SquareCropTransformation

private const val DEMO_URL = "https://picsum.photos/id/1084/640/640"

@Composable
fun rememberSamples(): List<Sample> {
    val common = remember {
        listOf(
            Sample("Original", DEMO_URL, emptyList()),
            Sample("Grayscale", DEMO_URL, listOf(GrayscaleTransformation())),
            Sample("Square crop", DEMO_URL, listOf(SquareCropTransformation())),
            Sample("Crop (top)", DEMO_URL, listOf(CropTransformation(CropTransformation.CropType.TOP))),
            Sample("Crop (center)", DEMO_URL, listOf(CropTransformation(CropTransformation.CropType.CENTER))),
            Sample("Crop (bottom)", DEMO_URL, listOf(CropTransformation(CropTransformation.CropType.BOTTOM))),
            Sample("Red tint", DEMO_URL, listOf(ColorFilterTransformation(0x50FF0000))),
        )
    }
    return common + platformSamples(DEMO_URL)
}

expect fun platformSamples(demoUrl: String): List<Sample>
