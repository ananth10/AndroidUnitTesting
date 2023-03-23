package com.ananth.artbooktesting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ananth.artbooktesting.R
import com.ananth.artbooktesting.roomdb.Art
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ArtRecyclerviewAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ArtRecyclerviewAdapter.ArtViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<Art>() {
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiff = AsyncListDiffer(this, diffUtil)

    var arts: List<Art>
        get() = recyclerListDiff.currentList
        set(value) = recyclerListDiff.submitList(value)

    class ArtViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.art_row, parent, false)
        return ArtViewHolder(view)
    }

    override fun getItemCount() = arts.size

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.rowImageView)
        val name = holder.itemView.findViewById<TextView>(R.id.artRowNameText)
        val artistNameText = holder.itemView.findViewById<TextView>(R.id.artRowArtistNameText)
        val yearText = holder.itemView.findViewById<TextView>(R.id.artYearText)

        val art = arts[position]
        holder.itemView.apply {
            name.text = "Name: ${art.name}"
            artistNameText.text = "ArtistName: ${art.artistName}"
            yearText.text = "Year: ${art.year.toString()}"
            println("IMGG::$imageView")
            glide.load(art.imageUrl).into(imageView)
        }
    }
}