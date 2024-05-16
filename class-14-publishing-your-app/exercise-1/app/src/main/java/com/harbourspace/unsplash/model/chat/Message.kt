package com.harbourspace.unsplash.model.chat

import com.google.firebase.Timestamp

data class Message(
    val content: String,
    val owner: String,
    val timestamp: Timestamp
)