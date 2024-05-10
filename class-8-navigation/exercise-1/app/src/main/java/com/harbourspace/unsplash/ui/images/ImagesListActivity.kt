package com.harbourspace.unsplash.ui.images

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.harbourspace.unsplash.ui.details.DetailsActivity
import com.harbourspace.unsplash.ui.theme.UnsplashTheme
import com.harbourspace.unsplash.utils.EXTRA_IMAGE

class ImagesListActivity : ComponentActivity() {

    private val imagesViewModel: ImagesViewModel by viewModels()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imagesViewModel.fetchImages()

        setContent {
            UnsplashTheme {

                val images = imagesViewModel.items.observeAsState(emptyList())
                val loading = imagesViewModel.loading.observeAsState(false)

                val pullRefreshState = rememberPullRefreshState(
                    refreshing = loading.value,
                    onRefresh = {
                        imagesViewModel.forceFetchImages()
                    }
                )

                Box(
                    Modifier.pullRefresh(pullRefreshState)
                ) {
                    ImagesListScreen(
                        images = images.value,
                        openDetails = { url -> openDetails(url) },
                        searchImages = { keyword -> imagesViewModel.searchImages(keyword) }
                    )

                    PullRefreshIndicator(loading.value, pullRefreshState, Modifier.align(Alignment.TopCenter))
                }

            }
        }
    }

    private fun openDetails(url: String?) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(EXTRA_IMAGE, url)
        startActivity(intent)
    }
}