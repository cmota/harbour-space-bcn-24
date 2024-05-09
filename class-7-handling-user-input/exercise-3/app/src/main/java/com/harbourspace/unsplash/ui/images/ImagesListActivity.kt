package com.harbourspace.unsplash.ui.images

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.livedata.observeAsState
import com.harbourspace.unsplash.ui.DetailsActivity
import com.harbourspace.unsplash.ui.theme.UnsplashTheme
import com.harbourspace.unsplash.utils.EXTRA_IMAGE

class ImagesListActivity : ComponentActivity() {

    private val imagesViewModel: ImagesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imagesViewModel.fetchImages()

        setContent {
            UnsplashTheme {

                val images = imagesViewModel.items.observeAsState(emptyList())

                ImagesListScreen(
                    images = images.value,
                    openDetails = { url -> openDetails(url) },
                    searchImages = { keyword -> imagesViewModel.searchImages(keyword)}
                )
            }
        }
    }

    private fun openDetails(url: String?) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(EXTRA_IMAGE, url)
        startActivity(intent)
    }
}