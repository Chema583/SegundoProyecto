package com.example.proyecto_android.presentation.detail

import com.example.proyecto_android.domain.model.Comment
import com.example.proyecto_android.domain.model.Country

data class DetailState(
    val country: Country? = null,
    val comments: List<Comment> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
