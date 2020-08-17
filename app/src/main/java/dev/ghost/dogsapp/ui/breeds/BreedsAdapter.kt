package dev.ghost.dogsapp.ui.breeds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.ghost.dogsapp.R
import dev.ghost.dogsapp.databinding.ItemBreedBinding
import dev.ghost.dogsapp.entities.BreedWithSubBreeds

class BreedsAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<BreedsAdapter.BreedsViewHolder>() {

    private var allItems: MutableList<BreedWithSubBreeds> = mutableListOf()

    inner class BreedsViewHolder(private val binding: ItemBreedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            fullBreed: BreedWithSubBreeds,
            itemClickListener: OnItemClickListener
        ) {
            binding.breed = fullBreed.breed
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                itemClickListener.onClick(fullBreed)
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
        holder.bind(currentBreed, onItemClickListener)
    }

    fun updateBreeds(newBreeds: List<BreedWithSubBreeds>) {
        allItems.clear()
        newBreeds.forEach {
            allItems.add(it)
        }
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClick(fullBreed: BreedWithSubBreeds)
//        fun onClick(subBreed: SubBreed)
    }
}