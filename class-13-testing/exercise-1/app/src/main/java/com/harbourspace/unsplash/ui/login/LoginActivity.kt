package com.harbourspace.unsplash.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.harbourspace.unsplash.ui.MainActivity
import com.harbourspace.unsplash.ui.theme.UnsplashTheme

private const val TAG = "LoginActivity"

class LoginActivity : ComponentActivity() {

    private lateinit var dialogErrorMessage: MutableState<String?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Firebase.auth.currentUser != null) {
            openMain()
            return
        }

        setContent {
            UnsplashTheme {

                dialogErrorMessage = remember { mutableStateOf(null) }

                val usernameValue = remember { mutableStateOf("") }
                val passwordValue = remember { mutableStateOf("") }

                LoginScreen(
                    onRegisterUser = { username, password -> registerUser(username, password) },
                    onAuthenticateUser = { username, password ->
                        authenticateUser(username, password)
                        usernameValue.value = username
                        passwordValue.value = password
                    }
                )

                if (dialogErrorMessage.value != null) {
                    AuthenticationDialog(
                        message = dialogErrorMessage.value,
                        dismissAction = { dialogErrorMessage.value = null },
                        confirmAction = { registerUser(usernameValue.value, passwordValue.value) }
                    )
                }
            }
        }
    }

    private fun openMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun authenticateUser(username: String?, password: String?) {
        Firebase.auth.signInWithEmailAndPassword(
            username ?: "",
            password ?: ""
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Log.d(TAG, "Authentication failed. Error: ${it.exception}")
                dialogErrorMessage.value = it.exception?.localizedMessage
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
                dialogErrorMessage.value = it.exception?.localizedMessage
                Toast.makeText(baseContext, it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}