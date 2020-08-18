package dev.ghost.dogsapp.viewmodel.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.ghost.dogsapp.R
import dev.ghost.dogsapp.databinding.ItemFavoutiteBinding
import dev.ghost.dogsapp.model.domain.BreedWithPhotos

class FavouritesAdapter(
    val onFavouriteClickListener: (favouriteBreed: BreedWithPhotos) -> Unit
) :
    RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>() {

    private var allItems = mutableListOf<BreedWithPhotos>()

    inner class FavouritesViewHolder(private val binding: ItemFavoutiteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favouriteBreed: BreedWithPhotos) {
            binding.favouriteBreed = favouriteBreed.breed
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onFavouriteClickListener(favouriteBreed)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val favBinding: ItemFavoutiteBinding = DataBindingUtil.inflate(
            LayoutInflater.from(
                parent.context
            ), R.layout.item_favoutite,
            parent, false
        )
        return FavouritesViewHolder(favBinding)
    }

    override fun getItemCount() = allItems.size

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val currentFavourite = allItems[position]
        holder.bind(currentFavourite)
    }

    fun updateData(newItems: List<BreedWithPhotos>) {
        allItems.clear()
        newItems.forEach {
            allItems.add(it)
        }
        notifyDataSetChanged()
    }

}