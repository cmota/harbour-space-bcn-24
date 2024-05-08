package com.harbourspace.unsplash.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.harbourspace.unsplash.R
import com.harbourspace.unsplash.api.UnsplashApiProvider
import com.harbourspace.unsplash.model.UnsplashItem
import com.harbourspace.unsplash.model.cb.UnsplashResult
import com.harbourspace.unsplash.ui.theme.UnsplashTheme

class ImagesListActivity : ComponentActivity(), UnsplashResult {

    private lateinit var images: MutableState<List<UnsplashItem>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val provider = UnsplashApiProvider()
        provider.fetchImages(this)

        setContent {
            UnsplashTheme {

                images = remember { mutableStateOf(emptyList()) }

                LazyColumn(
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                ) {
                    items(images.value) { image ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .padding(8.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { openDetails() },
                            color = Color.LightGray
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                Text(image.user?.name ?: "")

                                Spacer(modifier = Modifier.height(8.dp))

                                image.description?.let { description ->
                                    Text(
                                        text = description,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun openDetails() {
        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }

    override fun onDataFetchedSuccess(images: List<UnsplashItem>) {
        this.images.value = images
    }

    override fun onDataFetchedFailed() {
        Toast.makeText(applicationContext, "Unable to fetch images", Toast.LENGTH_SHORT).show()
    }
}