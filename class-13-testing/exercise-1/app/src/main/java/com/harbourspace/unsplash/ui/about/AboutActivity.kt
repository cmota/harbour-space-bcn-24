package com.harbourspace.unsplash.ui.about

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.harbourspace.unsplash.ui.theme.UnsplashTheme

class AboutActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UnsplashTheme {
                AboutScreen()
            }
        }
    }
}