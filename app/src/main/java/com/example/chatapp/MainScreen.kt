package com.example.chatapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chatapp.fuature.auth.signin.SignInScreen
import com.example.chatapp.fuature.auth.signup.SignUpScreen
import com.example.chatapp.fuature.chat.ChatScreen
import com.example.chatapp.fuature.home.HomeScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()

        val currentUser = FirebaseAuth.getInstance().currentUser

        val startDestination = if (currentUser != null) "HomeScreen" else "singin"


        NavHost(navController = navController, startDestination = startDestination) {

            composable("singin") {
                SignInScreen(navController = navController)
            }


            composable("login") {
                SignUpScreen(navController = navController)
            }
            composable("HomeScreen") {
                HomeScreen(navController = navController)
            }
            composable(
                "chat/{channelId}", arguments =
                listOf(
                    navArgument("channelId") {
                        type = NavType.StringType
                    })
            ) {
                val channelId = it.arguments?.getString("channelId") ?: ""
                ChatScreen(
                    navController = navController, channelId
                )
            }


        }

    }

}