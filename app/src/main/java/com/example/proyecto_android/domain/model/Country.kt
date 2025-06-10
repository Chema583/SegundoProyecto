package com.example.proyecto_android.domain.model

data class Country(
    val name: String,
    val capital: String,
    val flagUrl: String,
    val languages: List<String>,
    val population: Long
)
