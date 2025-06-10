package com.example.proyecto_android.domain.repository

import com.example.proyecto_android.domain.model.Comment
import com.example.proyecto_android.domain.model.Country

interface CountryRepository {
    suspend fun getAllCountries(): List<Country>
    suspend fun getCommentsForCountry(countryName: String): List<Comment>
    suspend fun addComment(comment: Comment)
}
