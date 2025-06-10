package com.example.proyecto_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.proyecto_android.presentation.navigation.AppNavGraph
import com.example.proyecto_android.presentation.navigation.Routes
import com.example.proyecto_android.ui.theme.Proyecto_AndroidTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Proyecto_AndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // Determinar pantalla inicial según el estado de sesión
                    val startDestination = if (FirebaseAuth.getInstance().currentUser != null) {
                        Routes.HOME
                    } else {
                        Routes.LOGIN
                    }

                    AppNavGraph(navController = navController, modifier = Modifier, startDestination = startDestination)
                }
            }
        }
    }
}
