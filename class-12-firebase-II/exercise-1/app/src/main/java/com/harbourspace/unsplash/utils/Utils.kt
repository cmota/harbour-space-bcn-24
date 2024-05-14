package com.harbourspace.unsplash.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


const val EXTRA_IMAGE = "extra_image"

fun formatDate(date: Date): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(date)

}