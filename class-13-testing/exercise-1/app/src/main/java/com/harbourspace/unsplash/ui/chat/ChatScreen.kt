package com.harbourspace.unsplash.ui.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.harbourspace.unsplash.R
import com.harbourspace.unsplash.model.chat.Message
import com.harbourspace.unsplash.ui.theme.Pink40
import com.harbourspace.unsplash.ui.theme.Pink80
import com.harbourspace.unsplash.ui.theme.Purple80
import com.harbourspace.unsplash.ui.theme.Typography
import com.harbourspace.unsplash.utils.formatDate
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    messages: List<Message>,
    sendMessage: (String) -> Unit
) {

    val message = remember { mutableStateOf("") }
    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.0f)
                ) {
                    OutlinedTextField(
                        value = message.value,
                        onValueChange = {
                            message.value = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(text = stringResource(id = R.string.messages_send))
                        }
                    )
                }

                Row {
                    Spacer(modifier = Modifier.width(16.dp))

                    IconButton(onClick = {
                        sendMessage(message.value)
                        message.value = ""
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = stringResource(R.string.description_send_message),
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            ChatMessages(messages)
        }
    }
}

@Composable
fun ChatMessages(messages: List<Message>) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    val lastItemIndex = messages.size - 1

    val scrollToBottom = remember { derivedStateOf { scrollState.layoutInfo } }.value.visibleItemsInfo.lastOrNull()?.index != messages.size

    LazyColumn(
        modifier = Modifier.padding(16.dp),
        state = scrollState
    ) {
        items(messages) {
            val self = Firebase.auth.currentUser?.email == it.owner
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (self) Arrangement.End else Arrangement.Start,
                verticalAlignment = Alignment.Bottom,
            ) {

                Text(
                    text = formatDate(it.timestamp.toDate()),
                    style = Typography.bodySmall
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (self) Purple80 else Pink80
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                    ) {
                        Text(
                            text = it.content
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = it.owner,
                            style = Typography.bodySmall
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    LaunchedEffect(scrollToBottom, lastItemIndex) {
        if (scrollToBottom && messages.isNotEmpty()) {
            scope.launch {
                scrollState.animateScrollToItem(lastItemIndex)
            }
        }
    }
}