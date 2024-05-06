package com.harbourspace.unsplash.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.harbourspace.unsplash.R

class ImagesListAdapter(private val images: List<Int>, val onClick: (Int) -> Unit)
    : RecyclerView.Adapter<ImagesListAdapter.ImagesViewHolder>() {

    class ImagesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.iv_preview)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImagesViewHolder(inflater.inflate(R.layout.item_image, parent, false))
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val image = images[position]
        holder.image.setImageResource(image)
        holder.image.setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}