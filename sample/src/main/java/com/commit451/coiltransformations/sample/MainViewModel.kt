package com.commit451.coiltransformations.sample

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.commit451.coiltransformations.ColorFilterTransformation
import com.commit451.coiltransformations.CropTransformation
import com.commit451.coiltransformations.MaskTransformation
import com.commit451.coiltransformations.SquareCropTransformation
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
