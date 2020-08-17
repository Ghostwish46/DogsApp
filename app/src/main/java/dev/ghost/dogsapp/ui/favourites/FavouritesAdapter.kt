package dev.ghost.dogsapp.ui.favourites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.ghost.dogsapp.R
import dev.ghost.dogsapp.databinding.ItemFavoutiteBinding
import dev.ghost.dogsapp.entities.FavouriteItem

class FavouritesAdapter(
    val onFavouriteClickListener: (favouriteItem: FavouriteItem) -> Unit
) :
    RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>() {

    private var allItems = mutableListOf<FavouriteItem>()

    inner class FavouritesViewHolder(private val binding: ItemFavoutiteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favouriteItem: FavouriteItem) {
            binding.favouriteItem = favouriteItem
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onFavouriteClickListener(favouriteItem)
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

    fun updateData(newItems: List<FavouriteItem>) {
        allItems.clear()
        newItems.forEach {
            allItems.add(it)
        }
        notifyDataSetChanged()
    }

}