package com.harbourspace.unsplash.ui.images

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material3.TabRow
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.harbourspace.unsplash.R
import com.harbourspace.unsplash.ui.about.AboutScreen
import com.harbourspace.unsplash.ui.collections.CollectionsScreen
import com.harbourspace.unsplash.ui.details.DetailsActivity
import com.harbourspace.unsplash.ui.navigation.BottomNavigationScreen
import com.harbourspace.unsplash.ui.theme.UnsplashTheme
import com.harbourspace.unsplash.utils.EXTRA_IMAGE

class ImagesListActivity : ComponentActivity() {

    private enum class TopTab(@StringRes val tab: Int) {
        HOME(R.string.tab_images),
        COLLECTIONS(R.string.tab_collections)
    }

    private val imagesViewModel: ImagesViewModel by viewModels()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imagesViewModel.fetchImages()
        imagesViewModel.fetchCollections()

        setContent {
            UnsplashTheme {

                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        val items = listOf(
                            BottomNavigationScreen.Home,
                            BottomNavigationScreen.About
                        )

                        val selected = remember { mutableIntStateOf(0) }

                        NavigationBar {
                            items.forEachIndexed { index, screen ->
                                NavigationBarItem(
                                    selected = selected.intValue == index,
                                    onClick = {
                                        selected.intValue = index
                                        navController.navigate(screen.route)
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = screen.drawResId,
                                            contentDescription = stringResource(id = screen.stringResId)
                                        )
                                    },
                                    label = {
                                        Text(
                                            stringResource(id = screen.stringResId)
                                        )
                                    })
                            }
                        }
                    }
                ) {
                    Column(
                        modifier = Modifier.padding(it)
                    ) {
                        NavHost(navController, startDestination = BottomNavigationScreen.Home.route) {
                            composable(BottomNavigationScreen.Home.route) {
                                MainScreen()
                            }

                            composable(BottomNavigationScreen.About.route) {
                                AboutScreen()
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun MainScreen() {
        val images = imagesViewModel.items.observeAsState(emptyList())
        val loading = imagesViewModel.loading.observeAsState(false)
        val collections = imagesViewModel.collections.observeAsState(emptyList())

        Column {

            val selected = remember { mutableIntStateOf(0) }

            val actions = listOf(TopTab.HOME, TopTab.COLLECTIONS)
            TabRow(
                selectedTabIndex = selected.intValue,
            ) {
                actions.forEachIndexed { index, tab ->
                    Tab(
                        modifier = Modifier.height(48.dp),
                        selected = selected.intValue == index,
                        onClick = { selected.intValue = index }
                    ) {
                        Text(
                            text = stringResource(id = TopTab.entries[index].tab)
                        )
                    }
                }
            }

            when (selected.intValue) {
                TopTab.HOME.ordinal -> {

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

                        PullRefreshIndicator(
                            loading.value,
                            pullRefreshState,
                            Modifier.align(Alignment.TopCenter)
                        )
                    }
                }

                TopTab.COLLECTIONS.ordinal -> {
                    CollectionsScreen(
                        collections = collections.value
                    )
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