package com.example.proyecto_android.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: CountryApiService
) {
    suspend fun fetchAllCountries(): List<CountryDto> {
        return api.getAllCountries()
    }
}
