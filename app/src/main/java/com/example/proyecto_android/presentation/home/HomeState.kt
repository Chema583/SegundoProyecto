package com.example.proyecto_android.presentation.home

import com.example.proyecto_android.domain.model.Country

data class HomeState(
    val isLoading: Boolean = false,
    val countries: List<Country> = emptyList(),
    val error: String? = null
)
