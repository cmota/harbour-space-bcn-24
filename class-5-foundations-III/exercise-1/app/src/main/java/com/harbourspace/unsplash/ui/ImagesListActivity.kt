package com.harbourspace.unsplash.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harbourspace.unsplash.R

class ImagesListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images_list)

        val images = listOf(
            R.drawable.bcn_la_sagrada_familia,
            R.drawable.bcn_casa_battlo,
            R.drawable.bcn_parc_guell,
            R.drawable.bcn_palau_montjuic,
            R.drawable.bcn_parc_guell_2
        )

        findViewById<RecyclerView>(R.id.rv_container).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ImagesListAdapter(images) {
                openDetails()
            }
        }
    }

    private fun openDetails() {
        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }
}