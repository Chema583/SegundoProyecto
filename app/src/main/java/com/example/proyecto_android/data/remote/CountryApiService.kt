package com.example.proyecto_android.data.remote

import retrofit2.http.GET

interface CountryApiService {
    @GET("v3.1/all")
    suspend fun getAllCountries(): List<CountryDto>
}
