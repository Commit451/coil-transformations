package com.commit451.coiltransformations.sample

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.transformations

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by bindViewModel()

    private val toolbar: Toolbar by bindView(R.id.toolbar)
    private val list: RecyclerView by bindView(R.id.list)
    private val detail: FrameLayout by bindView(R.id.detail)
    private val image: ImageView by bindView(R.id.image)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val listAdapter = ImageListAdapter(viewModel.screenLiveData::setValue)
        list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listAdapter
        }

        viewModel.screenLiveData.observe(this, Observer(::setScreen))
        viewModel.imagesLiveData.observe(this, Observer(listAdapter::submitList))
    }

    private fun setScreen(screen: Screen) {
        when (screen) {
            is Screen.List -> {
                list.isVisible = true
                detail.isVisible = false
            }
            is Screen.Detail -> {
                list.isVisible = false
                detail.isVisible = true
                image.load(screen.image.resource) {
                    transformations(screen.image.transformation)
                }
            }
        }
    }

    override fun onBackPressed() {
        if (!viewModel.onBackPressed()) {
            super.onBackPressed()
        }
    }
}
