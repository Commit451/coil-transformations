package com.commit451.coiltransformations.sample

import android.app.Application
import android.graphics.Color
import android.util.LruCache
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.commit451.coiltransformations.*
import com.commit451.coiltransformations.gpu.*
import kotlinx.coroutines.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    val screenLiveData = MutableLiveData<Screen>().apply { value = Screen.List }
    val imagesLiveData = MutableLiveData<List<Image>>()

    init {
        loadImages()
    }

    fun onBackPressed(): Boolean {
        return if (screenLiveData.value is Screen.Detail) {
            screenLiveData.value = Screen.List
            true
        } else {
            false
        }
    }

    private fun loadImages() = scope.launch(Dispatchers.IO) {
        val images = mutableListOf<Image>()
        val lruCache = LruCache<String, String>(4 * 1024 * 1024)
        images += Image(
                resource = R.drawable.scarlet_witch,
                transformation = NoOpTransformation()
        )
        images += Image(
                transformation = com.commit451.coiltransformations.facedetection.CenterOnFaceTransformation(lruCache,
                        0),
                resource = R.drawable.scarlet_witch
        )
        images += Image(
                transformation = com.commit451.coiltransformations.facedetection.CenterOnFaceTransformation(lruCache,
                        20),
                resource = R.drawable.scarlet_witch
        )

        images += Image(
                transformation = com.commit451.coiltransformations.facedetection.CenterOnFaceTransformation(lruCache,
                        40),
                resource = R.drawable.scarlet_witch
        )
        images += Image(
                transformation = com.commit451.coiltransformations.facedetection.CenterOnFaceTransformation(lruCache,
                        60),
                resource = R.drawable.scarlet_witch
        )
        images += Image(
                transformation = com.commit451.coiltransformations.facedetection.CenterOnFaceTransformation(lruCache,
                        80),
                resource = R.drawable.scarlet_witch
        )
        images += Image(
                transformation = com.commit451.coiltransformations.facedetection.CenterOnFaceTransformation(lruCache,
                        100),
                resource = R.drawable.scarlet_witch
        )
        images += Image(
            transformation = MaskTransformation(context, R.drawable.mask_starfish)
        )
        images += Image(
            transformation = CropTransformation(CropTransformation.CropType.CENTER),
            resource = R.drawable.demo
        )
        images += Image(
            transformation = SquareCropTransformation(),
            resource = R.drawable.demo
        )
        images += Image(
            transformation = ColorFilterTransformation(Color.argb(80, 255, 0, 0))
        )
        images += Image(
            transformation = BrightnessFilterTransformation(context, 0.5f)
        )
        images += Image(
            transformation = ContrastFilterTransformation(context, 2.0f)
        )
        images += Image(
            transformation = InvertFilterTransformation(context)
        )
        images += Image(
            transformation = KuwaharaFilterTransformation(context)
        )
        images += Image(
            transformation = PixelationFilterTransformation(context, 20.0f)
        )
        images += Image(
            transformation = SepiaFilterTransformation(context)
        )
        images += Image(
            transformation = SketchFilterTransformation(context)
        )
        images += Image(
            transformation = SwirlFilterTransformation(context)
        )
        images += Image(
            transformation = ToonFilterTransformation(context)
        )
        images += Image(
            transformation = VignetteFilterTransformation(context)
        )
        imagesLiveData.postValue(images)
    }

    override fun onCleared() {
        scope.cancel()
    }
}
