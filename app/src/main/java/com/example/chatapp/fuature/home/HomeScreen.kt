package com.example.chatapp.fuature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val viewModel = hiltViewModel<HomeScreenViewModel>()

    val channelName = remember { mutableStateOf("") }
    val channels = viewModel.channels.collectAsState()

    val filteredChannels = channels.value.filter {
        it.name.contains(channelName.value, ignoreCase = true)
    }

    val addChannel = remember {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState()



    Scaffold(
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(50))
                    .background(color = Color.Blue)
                    .clickable {
                        addChannel.value = true
                    }

            ) {
                Text(
                    text = "Create Channel",
                    modifier = Modifier.padding(16.dp),
                    color = Color.White
                )
            }


        },
        containerColor = Color.Black


    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()


        ) {

            LazyColumn {
                item {

                    Text(
                        text = "Channels",
                        color = Color.Gray,
                        style = TextStyle(fontSize = 30.sp),
                        modifier = Modifier.padding(4.dp)
                    )


                    Spacer(modifier = Modifier.padding(8.dp))
                    OutlinedTextField(
                        value = channelName.value,
                        onValueChange = { channelName.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        textStyle = TextStyle(fontSize = 20.sp, color = Color.White),
                        maxLines = 1,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        },
                    )
                    HorizontalDivider(color = Color.Gray, thickness = 3.dp)
                    Spacer(modifier = Modifier.padding(5.dp))
                }

                items(
                    filteredChannels,

                ) { channel ->


                    ChannelItem(channel = channel.name, onChannelSelected = {
                        navController.navigate("chat/${channel.id}&${channel.name}")
                    }
                    )


                }
            }

        }
    }
    if (addChannel.value) {

        ModalBottomSheet(
            onDismissRequest = { addChannel.value = false },
            sheetState = sheetState,
        ) {
            AddChannelDialog {
                viewModel.addChannel(it)
                addChannel.value = false

            }

        }
    }

}

@Composable
fun AddChannelDialog(onAddChannel: (String) -> Unit) {
    val channelName = remember {
        mutableStateOf("")
    }
    Column(

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(50.dp),
    ) {
        Text(text = "Add Channel")
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(value = channelName.value, onValueChange = {
            channelName.value = it
        }, label = { Text(text = "Channel Name") }, singleLine = true)
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = { onAddChannel(channelName.value) },
            modifier = Modifier.fillMaxWidth()) {
            Text(text = "Add")
        }
    }
}


@Composable
fun ChannelItem(channel: String, onChannelSelected: () -> Unit) {

    val randomColor = remember {
        Color(
            red = Random.nextFloat(),
            green = Random.nextFloat(),
            blue = Random.nextFloat(),
            alpha = 1f
        )
    }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .fillMaxHeight()

            .clickable { onChannelSelected() }
            .clip(RoundedCornerShape(8.dp))
            .background(Color.DarkGray),


        ) {
        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically)
                .clip(CircleShape)
                .size(75.dp)
                .background(randomColor, CircleShape)

                .wrapContentSize(Alignment.Center),
            text = channel[0].toString().uppercase(),
            fontSize = 52.sp,
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = channel,
            fontSize = 25.sp,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 8.dp)
        )

    }
}
