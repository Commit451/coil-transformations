package com.commit451.coiltransformations.sample

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.commit451.coiltransformations.sample.ImageListAdapter.ViewHolder

class ImageListAdapter(
    private val setScreen: (Screen) -> Unit
) : ListAdapter<Image, ViewHolder>(Callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.list_item))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.image.apply {
            load(item.resource) {
                transformations(item.transformation)
            }

            setOnClickListener {
                setScreen(Screen.Detail(item))
            }
        }
        holder.text.text = item.name
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val text: TextView = itemView.findViewById(R.id.text)
    }

    private object Callback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(old: Image, new: Image) = old.transformation == new.transformation
        override fun areContentsTheSame(old: Image, new: Image) = old == new
    }
}
