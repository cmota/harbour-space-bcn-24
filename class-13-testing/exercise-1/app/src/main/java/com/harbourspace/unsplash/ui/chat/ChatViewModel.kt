package com.harbourspace.unsplash.ui.chat

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.harbourspace.unsplash.model.chat.Message

private const val TAG = "ChatViewModel"
private const val MODULE = "HS-MOBILE_DEVELOPMENT"

class ChatViewModel: ViewModel() {

    private val _messages = MutableLiveData<List<Message>>()
    val messages = _messages

    fun sendMessage(content: String) {
        val username = Firebase.auth.currentUser?.email ?: ""
        val message = Message(
            content = content,
            owner = username,
            timestamp = Timestamp.now()
        )

        Firebase.firestore
            .collection(MODULE)
            .document()
            .set(message)
            .addOnSuccessListener {
                Log.d(TAG, "Message sent")
            }
            .addOnFailureListener {
                Log.e(TAG, "Unable to send message. Reason=$it")
            }
    }

    fun fetchMessages() {
        val docs = Firebase.firestore.collection(MODULE).orderBy("timestamp")
        docs.addSnapshotListener { snapshot, e ->
            if (snapshot?.documents?.isNotEmpty() == true) {
                val messages = mutableListOf<Message>()
                for (document in snapshot.documents) {
                    messages.add(
                        Message(
                            content = document.get("content").toString(),
                            timestamp = document.get("timestamp") as Timestamp,
                            owner = document.get("owner").toString()
                        )
                    )
                }

                _messages.value = messages
            }
        }
    }
}