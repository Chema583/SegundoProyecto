package com.example.proyecto_android.domain.usecase

import com.example.proyecto_android.domain.model.Comment
import com.example.proyecto_android.domain.repository.CountryRepository
import javax.inject.Inject

class GetCommentsForCountryUseCase @Inject constructor(
    private val repository: CountryRepository
) {
    suspend operator fun invoke(countryName: String): List<Comment> {
        return repository.getCommentsForCountry(countryName)
    }
}
