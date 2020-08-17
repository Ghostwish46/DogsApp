package dev.ghost.dogsapp.ui.favourites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ThemedSpinnerAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.ghost.dogsapp.MainActivity
import dev.ghost.dogsapp.R
import dev.ghost.dogsapp.ui.images.ImagesActivity
import kotlinx.android.synthetic.main.fragment_favourites.view.*

class FavouritesFragment : Fragment() {

    private lateinit var favouriteViewModel: FavouritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favouriteViewModel =
            ViewModelProvider(this).get(FavouritesViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_favourites, container, false)

        val favouritesAdapter = FavouritesAdapter()
        {
            val intentImages = Intent(context, ImagesActivity::class.java)
            intentImages.putExtra(MainActivity.EXTRA_ITEM, it)
            context?.startActivity(intentImages)
        }

        root.recyclerViewFavourites.apply {
            adapter = favouritesAdapter
            layoutManager = LinearLayoutManager(root.context)
        }

        favouriteViewModel.favouriteItems.observe(viewLifecycleOwner, Observer {
            favouritesAdapter.updateData(it)
        })

        return root
    }
}