package com.harbourspace.unsplash.utils

import androidx.core.util.PatternsCompat

fun String?.isValidEmail(): Boolean {
    return !isNullOrEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()
}