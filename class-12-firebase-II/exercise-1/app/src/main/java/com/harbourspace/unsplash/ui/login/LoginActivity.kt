package com.harbourspace.unsplash.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.harbourspace.unsplash.ui.MainActivity
import com.harbourspace.unsplash.ui.theme.UnsplashTheme

private const val TAG = "LoginActivity"

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Firebase.auth.currentUser != null) {
            openMain()
            return
        }

        setContent {
            UnsplashTheme {
                LoginScreen(
                    onRegisterUser = { username, password -> registerUser(username, password) },
                    onAuthenticateUser = { username, password -> authenticateUser(username, password)}
                )
            }
        }
    }

    private fun openMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun authenticateUser(username: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(
            username,
            password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Log.d(TAG, "Authentication failed. Error: ${it.exception}")
                Toast.makeText(baseContext, it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(username: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(
            username,
            password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Log.d(TAG, "Authentication failed. Error: ${it.exception}")
                Toast.makeText(baseContext, it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}