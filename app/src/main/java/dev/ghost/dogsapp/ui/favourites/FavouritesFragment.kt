package dev.ghost.dogsapp.ui.favourites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ghost.dogsapp.ui.MainActivity
import dev.ghost.dogsapp.R
import dev.ghost.dogsapp.ui.images.ImagesActivity
import dev.ghost.dogsapp.viewmodel.favourites.FavouritesAdapter
import dev.ghost.dogsapp.viewmodel.favourites.FavouritesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_favourites.view.*

class FavouritesFragment : Fragment() {

    private lateinit var favouriteViewModel: FavouritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).titleMain.text = getString(R.string.title_favourites)

        favouriteViewModel =
            ViewModelProvider(this).get(FavouritesViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_favourites, container, false)

        val favouritesAdapter =
            FavouritesAdapter()
            {
                val intentImages = Intent(context, ImagesActivity::class.java)
                intentImages.putExtra(MainActivity.EXTRA_ITEM, it)
                intentImages.putExtra(MainActivity.FLAG_FAVOURITE, true)
                context?.startActivity(intentImages)
            }

        root.recyclerViewFavourites.apply {
            adapter = favouritesAdapter
            layoutManager = LinearLayoutManager(root.context)
        }

        favouriteViewModel.favouriteBreeds.observe(
            viewLifecycleOwner,
            Observer { breedsWithPhotos ->
                val distinctList = breedsWithPhotos.distinctBy {
                    it.breed.name
                }
                distinctList.forEach {
                    it.breedPhotos = it.breedPhotos.filter {
                        it.liked
                    }
                }

                favouritesAdapter.updateData(distinctList)
            })
        return root
    }
}