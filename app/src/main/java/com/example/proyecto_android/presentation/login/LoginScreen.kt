package com.example.proyecto_android.presentation.login

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyecto_android.presentation.navigation.Routes
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    var oneTapClient: SignInClient? by remember { mutableStateOf(null) }

    var isSigningIn by remember { mutableStateOf(false) }

    // Google sign-in result launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        val credential = oneTapClient?.getSignInCredentialFromIntent(result.data)
        val idToken = credential?.googleIdToken

        if (idToken != null) {
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Bienvenido!", Toast.LENGTH_SHORT).show()
                        navController.navigate(Routes.HOME) {
                            popUpTo(0) { inclusive = true }
                        }
                    } else {
                        Toast.makeText(context, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
                        Log.e("LoginScreen", "Firebase Auth error", task.exception)
                    }
                }
        } else {
            Toast.makeText(context, "ID Token es null", Toast.LENGTH_SHORT).show()
        }

        isSigningIn = false
    }

    LaunchedEffect(Unit) {
        oneTapClient = Identity.getSignInClient(context)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Inicia sesión con Google", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    isSigningIn = true
                    val signInRequest = BeginSignInRequest.builder()
                        .setGoogleIdTokenRequestOptions(
                            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                .setSupported(true)
                                .setServerClientId("TU_CLIENT_ID_WEB_AQUÍ")
                                .setFilterByAuthorizedAccounts(false)
                                .build()
                        )
                        .setAutoSelectEnabled(true)
                        .build()

                    oneTapClient?.beginSignIn(signInRequest)
                        ?.addOnSuccessListener { result ->
                            launcher.launch(
                                IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                            )
                        }
                        ?.addOnFailureListener {
                            Toast.makeText(context, "Fallo al iniciar sesión", Toast.LENGTH_SHORT).show()
                            Log.e("LoginScreen", "One Tap Error", it)
                            isSigningIn = false
                        }
                },
                enabled = !isSigningIn
            ) {
                Text("Iniciar con Google")
            }
        }
    }
}
