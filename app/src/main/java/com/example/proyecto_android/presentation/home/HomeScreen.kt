package com.example.proyecto_android.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.proyecto_android.domain.model.Country
import com.example.proyecto_android.presentation.navigation.Routes

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsState().value

    when {
        state.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        state.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: ${state.error}")
            }
        }
        else -> {
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                items(state.countries) { country ->
                    CountryItem(country) {
                        navController.navigate("${Routes.DETAIL}/${country.name}")
                    }
                }
            }
        }
    }
}

@Composable
fun CountryItem(country: Country, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(country.flagUrl),
                contentDescription = country.name,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp)
            )
            Column {
                Text(text = country.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Capital: ${country.capital}",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
