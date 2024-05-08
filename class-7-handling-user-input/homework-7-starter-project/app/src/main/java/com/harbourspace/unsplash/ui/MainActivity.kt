package com.harbourspace.unsplash.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harbourspace.unsplash.ui.about.AboutActivity
import com.harbourspace.unsplash.ui.details.DetailsActivity
import com.harbourspace.unsplash.ui.theme.UnsplashTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("MainActivity", "onCreate")
        Toast.makeText(applicationContext, "onCreate", Toast.LENGTH_SHORT).show()

        setContent {
            UnsplashTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Greeting("Android")

                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(
                        onClick = { openExercise() },
                        modifier = Modifier.defaultMinSize(minWidth = 200.dp)
                    ) {
                            Text("Exercise!")
                    }

                    Button(
                        onClick = { openDetails() },
                        modifier = Modifier.defaultMinSize(minWidth = 200.dp)
                    ) {
                        Text("Details")
                    }

                    Button(
                        onClick = { openImagesList() },
                        modifier = Modifier.defaultMinSize(minWidth = 200.dp)
                    ) {
                        Text("Images")
                    }

                    Button(
                        onClick = { openAbout() },
                        modifier = Modifier.defaultMinSize(minWidth = 200.dp)
                    ) {
                        Text("About")
                    }
                }
            }
        }
    }

    private fun openDialer(){
        val intent = Intent(Intent.ACTION_DIAL)
        startActivity(intent)
    }

    private fun openExercise() {
        val intent = Intent(this, ExerciseActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openDetails() {
        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }

    private fun openImagesList() {
        val intent = Intent(this, ImagesListActivity::class.java)
        startActivity(intent)
    }

    private fun openAbout() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        Log.d("MainActivity", "onResume")
        Toast.makeText(applicationContext, "onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        Log.d("MainActivity", "onPause")
        Toast.makeText(applicationContext, "onPause", Toast.LENGTH_SHORT).show()

        super.onPause()
    }

    override fun onDestroy() {
        Log.d("MainActivity", "onDestroy")
        super.onDestroy()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UnsplashTheme {
        Greeting("Android")
    }
}