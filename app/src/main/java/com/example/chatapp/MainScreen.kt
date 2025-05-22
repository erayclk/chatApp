package com.example.chatapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.fuature.auth.signin.SignInScreen
import com.example.chatapp.fuature.auth.signup.SignUpScreen
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


        }

    }

}