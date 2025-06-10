package com.example.proyecto_android.domain.usecase

import com.example.proyecto_android.domain.model.Country
import com.example.proyecto_android.domain.repository.CountryRepository
import javax.inject.Inject

class GetAllCountriesUseCase @Inject constructor(
    private val repository: CountryRepository
) {
    suspend operator fun invoke(): List<Country> {
        return repository.getAllCountries()
    }
}
