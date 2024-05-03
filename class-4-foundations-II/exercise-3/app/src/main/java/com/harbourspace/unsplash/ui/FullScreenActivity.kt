package com.harbourspace.unsplash.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.harbourspace.unsplash.R

class FullScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_full_screen)

        findViewById<ImageView>(R.id.iv_preview).setOnClickListener {
            finish()
        }
    }
}