package dev.ghost.dogsapp.ui

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dev.ghost.dogsapp.R
import java.lang.Exception


// Picasso binding adapter for images loading.
@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String) {
    if (url != "") {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.dog_placeholder)
            .error(R.drawable.dog_error)
            .into(imageView, object : Callback {
                override fun onSuccess() {
                }

                override fun onError(e: Exception?) {
                    Log.d("ERROR", e?.message.toString())
                }
            })
    }
}
