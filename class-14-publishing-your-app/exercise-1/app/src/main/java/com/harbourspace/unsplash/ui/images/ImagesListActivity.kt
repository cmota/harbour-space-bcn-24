package com.harbourspace.unsplash.ui.images

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TabRow
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.harbourspace.unsplash.R
import com.harbourspace.unsplash.ui.about.AboutScreen
import com.harbourspace.unsplash.ui.chat.ChatScreen
import com.harbourspace.unsplash.ui.chat.ChatViewModel
import com.harbourspace.unsplash.ui.collections.CollectionsScreen
import com.harbourspace.unsplash.ui.details.DetailsActivity
import com.harbourspace.unsplash.ui.navigation.BottomNavigationScreen
import com.harbourspace.unsplash.ui.theme.UnsplashTheme
import com.harbourspace.unsplash.utils.EXTRA_IMAGE
import com.harbourspace.unsplash.utils.EXTRA_IMAGE_NAME
import kotlinx.coroutines.launch

class ImagesListActivity : ComponentActivity() {

    private enum class TopTab(@StringRes val tab: Int) {
        HOME(R.string.tab_images),
        COLLECTIONS(R.string.tab_collections)
    }

    private val chatViewModel: ChatViewModel by viewModels()
    private val imagesViewModel: ImagesViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                Color.TRANSPARENT,
                Color.TRANSPARENT,
                detectDarkMode = { false }
            )
        )

        chatViewModel.fetchMessages()

        imagesViewModel.fetchImages()
        imagesViewModel.fetchCollections()

        setContent {
            UnsplashTheme {

                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    contentWindowInsets = ScaffoldDefaults
                        .contentWindowInsets
                        .exclude(WindowInsets.navigationBars)
                        .exclude(WindowInsets.ime),
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = {
                       TopAppBar(
                           title = {
                               Text(text = stringResource(id = R.string.app_name))
                           }
                       )
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            Toast.makeText(this@ImagesListActivity, "Hello everyone!", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(id = R.string.description_add)
                            )
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    bottomBar = {
                        val items = listOf(
                            BottomNavigationScreen.Home,
                            BottomNavigationScreen.Chat,
                            BottomNavigationScreen.About
                        )

                        val selected = remember { mutableIntStateOf(0) }

                        Column(
                            modifier = Modifier
                                .navigationBarsPadding()
                                .imePadding()
                        ) {
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
                    }
                ) {
                    val messages = chatViewModel.messages.observeAsState(emptyList())

                    Column(
                        modifier = Modifier.padding(it)
                    ) {
                        NavHost(navController, startDestination = BottomNavigationScreen.Home.route) {
                            composable(BottomNavigationScreen.Home.route) {
                                MainScreen()
                            }

                            composable(BottomNavigationScreen.Chat.route) {
                                ChatScreen(
                                    messages = messages.value,
                                    sendMessage = { content -> chatViewModel.sendMessage(content)}
                                )
                            }

                            composable(BottomNavigationScreen.About.route) {
                                AboutScreen()
                            }
                        }
                    }

                    val scope = rememberCoroutineScope()

                    LaunchedEffect(key1 = Unit) {
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = applicationContext.getString(R.string.images_snackbar_title),
                                actionLabel = applicationContext.getString(R.string.images_snackbar_action),
                                duration = SnackbarDuration.Short
                            )

                            when(result) {
                                SnackbarResult.ActionPerformed -> {
                                    startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                                }

                                else -> {
                                    //Do nothing
                                }
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
                            openDetails = { url, name -> openDetails(url, name) },
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

    private fun openDetails(url: String?, name: String?) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(EXTRA_IMAGE, url)
        intent.putExtra(EXTRA_IMAGE_NAME, name)
        startActivity(intent)
    }
}