package com.harbourspace.unsplash.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.ui.graphics.vector.ImageVector
import com.harbourspace.unsplash.R

sealed class BottomNavigationScreen(
    val route: String,
    @StringRes val stringResId: Int,
    val drawResId: ImageVector
) {
    data object Home : BottomNavigationScreen("Home", R.string.navigation_home, Icons.Default.Home)

    data object Chat: BottomNavigationScreen("Chat", R.string.navigation_chat, Icons.Default.MailOutline)

    data object About : BottomNavigationScreen("About", R.string.navigation_about, Icons.Default.Info)
}