package dev.ghost.dogsapp.viewmodel.breeds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.ghost.dogsapp.R
import dev.ghost.dogsapp.databinding.ItemBreedBinding
import dev.ghost.dogsapp.model.domain.BreedWithPhotos

class BreedsAdapter(val onBreedClickListener: (breedWithPhotos: BreedWithPhotos) -> Unit) :
    RecyclerView.Adapter<BreedsAdapter.BreedsViewHolder>() {

    private var allItems: MutableList<BreedWithPhotos> = mutableListOf()

    inner class BreedsViewHolder(private val binding: ItemBreedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            fullBreed: BreedWithPhotos
        ) {
            binding.breed = fullBreed.breed
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                onBreedClickListener(fullBreed)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedsViewHolder {
        val breedBinding: ItemBreedBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_breed, parent, false
            )
        return BreedsViewHolder(breedBinding)
    }

    override fun getItemCount() =
        allItems.size

    override fun onBindViewHolder(holder: BreedsViewHolder, position: Int) {
        val currentBreed = allItems[position]
        holder.bind(currentBreed)
    }

    fun updateBreeds(newBreeds: List<BreedWithPhotos>) {
        allItems.clear()
        newBreeds.forEach {
            allItems.add(it)
        }
        notifyDataSetChanged()
    }
}