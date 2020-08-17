package dev.ghost.dogsapp.ui.subBreeds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.ghost.dogsapp.R
import dev.ghost.dogsapp.databinding.ItemSubBreedBinding
import dev.ghost.dogsapp.entities.SubBreedWithPhotos

class SubBreedsAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<SubBreedsAdapter.SubBreedsViewHolder>() {

    private var allItems: MutableList<SubBreedWithPhotos> = mutableListOf()

    inner class SubBreedsViewHolder(private val binding: ItemSubBreedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            fullSubBreed: SubBreedWithPhotos,
            itemClickListener: OnItemClickListener
        ) {
            binding.subBreed = fullSubBreed.subBreed
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                itemClickListener.onClick(fullSubBreed)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubBreedsViewHolder {
        val subBreedBinding: ItemSubBreedBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_sub_breed, parent, false
            )
        return SubBreedsViewHolder(subBreedBinding)
    }

    override fun getItemCount() =
        allItems.size

    override fun onBindViewHolder(holder: SubBreedsViewHolder, position: Int) {
        val currentBreed = allItems[position]
        holder.bind(currentBreed, onItemClickListener)
    }

    fun updateSubBreeds(newBreeds: List<SubBreedWithPhotos>) {
        allItems.clear()
        newBreeds.forEach {
            allItems.add(it)
        }
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClick(fullSubBreed: SubBreedWithPhotos)
    }
}