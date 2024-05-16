package com.harbourspace.unsplash.ui.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.harbourspace.unsplash.R
import com.harbourspace.unsplash.model.UnsplashItem

@Composable
fun ImagesListScreen(
    images: List<UnsplashItem>,
    openDetails: (String?, String?) -> Unit,
    searchImages: (String?) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
    ) {
        item {
            val search = remember { mutableStateOf("") }

            OutlinedTextField(
                value = search.value,
                onValueChange = { value ->
                    search.value = value
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.images_search_hint),
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                },
                leadingIcon = {
                    Image(
                        imageVector = Icons.Default.Search,
                        modifier = Modifier.size(25.dp),
                        contentDescription = stringResource(R.string.description_search),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions {
                    searchImages(search.value)
                }
            )
        }

        items(images) { image ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { openDetails(image.urls?.regular, image.description) },
                color = Color.LightGray
            ) {

                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image.urls?.regular)
                        .build()
                )

                Image(
                    painter = painter,
                    contentDescription = stringResource(id = R.string.description_image_preview),
                    modifier = Modifier
                        .fillMaxSize()
                        .placeholder(
                            visible = painter.state !is AsyncImagePainter.State.Success,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = Color.LightGray
                        ),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = image.user?.name ?: "",
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    image.description?.let { description ->
                        Text(
                            text = description,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}