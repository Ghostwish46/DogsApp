package dev.ghost.dogsapp.ui.images

import android.provider.DocumentsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.ghost.dogsapp.R
import dev.ghost.dogsapp.databinding.ItemImageBinding
import dev.ghost.dogsapp.entities.BreedWithSubBreeds
import dev.ghost.dogsapp.entities.ItemImage

class ImagesAdapter(
    val onLikedClickListener: (itemImage:ItemImage) -> Unit
) : RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    private var allItems: MutableList<ItemImage> = mutableListOf()

    inner class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun bind(itemImage: ItemImage) {
            binding.apply {
                this.itemImage = itemImage
                executePendingBindings()
                floatingActionButtonItemImageLiked.setOnClickListener {
                    onLikedClickListener(itemImage)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemBinding: ItemImageBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_image,
                parent, false
            )
        return ImageViewHolder(itemBinding)
    }

    override fun getItemCount() = allItems.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        var currentItem = allItems[position]
        holder.bind(currentItem)
    }

    fun updateImages(newBreeds: List<ItemImage>) {
        allItems.clear()
        newBreeds.forEach {
            allItems.add(it)
        }
        notifyDataSetChanged()
    }
}