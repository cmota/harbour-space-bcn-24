package com.harbourspace.unsplash.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.harbourspace.unsplash.R

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        findViewById<ImageView>(R.id.iv_preview).setOnClickListener {
            openFullScreenActivity()
        }
    }

    private fun openFullScreenActivity() {
        val intent = Intent(applicationContext, FullScreenActivity::class.java)
        startActivity(intent)
    }
}