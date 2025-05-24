package com.example.chatapp.fuature.chat

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp.fuature.home.ChannelItem
import com.example.chatapp.model.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlin.random.Random


@Composable
fun ChatScreen(navController: NavController, channelId: String,channelName: String) {

    Scaffold (modifier = Modifier.background(Color.Black)){
        Column(modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .padding(it)) {
            val viewModel: ChatScreenViewModel = hiltViewModel()
            LaunchedEffect(key1 = true) {
                viewModel.listenForMessages(channelId)
            }
            val messages = viewModel.messages.collectAsState()
            ChatMessage(
                channelName = channelName,
                massages = messages.value,
                onSendMessage = { messages ->
                    viewModel.sendMessage(channelId, messages)
                }
            )
            Log.d("ChatScreen", "ChatScreen: ${channelName}")
        }
    }



}

@Composable
fun ChatMessage(
    channelName: String,
    massages: List<Message>,
    onSendMessage: (String) -> Unit,
) {

    val messageText = remember { mutableStateOf("") }

    val hideKeyboardController = LocalSoftwareKeyboardController.current


    Box(
        modifier = Modifier.fillMaxSize(),

        ) {
        LazyColumn {


            item {
                ChannelItem(channelName, onChannelSelected = {
                    Log.d("ChatMessage", "ChatMessage: ${channelName}")
                })
            }

            items(massages) { message ->

                ChatBubble(message = message)


            }


        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {
                OutlinedTextField(
                    value = messageText.value,
                    onValueChange = { messageText.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
                    placeholder = { Text(text = "Enter your message") },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (messageText.value.isNotBlank()) {
                                onSendMessage(messageText.value)
                                messageText.value = ""
                            }
                            hideKeyboardController?.hide()
                        }
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
            }

            IconButton(
                onClick = {
                    if (messageText.value.isNotBlank()) {
                        onSendMessage(messageText.value)
                        messageText.value = ""
                    }
                },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(50)
                    )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }


}

@Composable
fun ChatBubble(message: Message) {

    val isCurrentUser = message.senderId == Firebase.auth.currentUser?.uid


    val bubbleColor = if (isCurrentUser) {
        Color.Blue
    } else {
        Color.Gray
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(color = bubbleColor, shape = RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "${message.senderName}:${message.message}", color = Color.White, modifier = Modifier.padding(8.dp)
            )
        }
    }

}
