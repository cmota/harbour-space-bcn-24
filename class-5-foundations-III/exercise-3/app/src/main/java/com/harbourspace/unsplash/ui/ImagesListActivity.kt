package com.harbourspace.unsplash.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.harbourspace.unsplash.R
import com.harbourspace.unsplash.ui.theme.UnsplashTheme

class ImagesListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val images = listOf(
            R.drawable.bcn_la_sagrada_familia,
            R.drawable.bcn_casa_battlo,
            R.drawable.bcn_parc_guell,
            R.drawable.bcn_palau_montjuic,
            R.drawable.bcn_parc_guell_2
        )

        setContent {
            UnsplashTheme {

                LazyColumn(
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                ) {
                    items(images) { image ->
                        Image(
                            painter = painterResource(id = image),
                            contentDescription = stringResource(id = R.string.description_image_preview),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { openDetails() },
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }

    private fun openDetails() {
        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }
}