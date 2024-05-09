package com.harbourspace.unsplash.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.harbourspace.unsplash.R
import com.harbourspace.unsplash.utils.EXTRA_IMAGE

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        val image = intent.extras?.getString(EXTRA_IMAGE)

        findViewById<ImageView>(R.id.iv_preview).load(image) {
            crossfade(true)
            placeholder(R.drawable.ic_progress)
            error(R.drawable.ic_error)
        }

        findViewById<ImageView>(R.id.iv_preview).setOnClickListener {
            openFullScreenActivity()
        }
    }

    private fun openFullScreenActivity() {
        val intent = Intent(applicationContext, FullScreenActivity::class.java)
        startActivity(intent)
    }
}