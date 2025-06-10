package com.example.proyecto_android.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.proyecto_android.domain.model.Country

@Composable
fun DetailScreen(
    countryName: String,
    viewModel: DetailViewModel
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCountryDetails(countryName)
    }

    when {
        state.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        state.error != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${state.error}")
            }
        }
        else -> {
            state.country?.let { country ->
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)) {

                    CountryDetails(country)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Comentarios", style = MaterialTheme.typography.titleMedium)

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        items(state.comments) { comment ->
                            Text("- ${comment.content}")
                        }
                    }

                    AddCommentForm(
                        onAdd = { content ->
                            viewModel.addComment(country.name, content)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CountryDetails(country: Country) {
    Column {
        Text(country.name, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(4.dp))
        Text("Capital: ${country.capital}")
        Text("Población: ${country.population}")
        Text("Idiomas: ${country.languages.joinToString(", ")}")
        Spacer(modifier = Modifier.height(8.dp))
        AsyncImage(
            model = country.flagUrl,
            contentDescription = "Bandera de ${country.name}",
            modifier = Modifier.height(100.dp)
        )
    }
}

@Composable
fun AddCommentForm(onAdd: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Añadir comentario") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (text.isNotBlank()) {
                    onAdd(text)
                    text = ""
                }
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 8.dp)
        ) {
            Text("Publicar")
        }
    }
}
