package dev.ghost.dogsapp.viewmodel.images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.ghost.dogsapp.R
import dev.ghost.dogsapp.databinding.ItemImageBinding
import dev.ghost.dogsapp.model.entities.BreedPhoto

class ImagesAdapter(
    val onLikedClickListener: (breedPhoto: BreedPhoto) -> Unit
) : RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    private var allItems: MutableList<BreedPhoto> = mutableListOf()

    inner class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun bind(breedPhoto: BreedPhoto) {
            binding.apply {
                this.breedPhoto = breedPhoto
                executePendingBindings()
                floatingActionButtonItemImageLiked.setOnClickListener {
                    onLikedClickListener(breedPhoto)
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
        val currentItem = allItems[position]
        holder.bind(currentItem)
    }

    fun updateImages(newPhotos: List<BreedPhoto>) {
        allItems.clear()
        newPhotos.forEach {
            allItems.add(it)
        }
        notifyDataSetChanged()
    }
}