package com.example.proyecto_android.data.repository

import com.example.proyecto_android.data.local.LocalDataSource
import com.example.proyecto_android.data.model.toDomain
import com.example.proyecto_android.data.model.toEntity
import com.example.proyecto_android.data.remote.RemoteDataSource
import com.example.proyecto_android.domain.model.Comment
import com.example.proyecto_android.domain.model.Country
import com.example.proyecto_android.domain.repository.CountryRepository
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CountryRepository {

    override suspend fun getAllCountries(): List<Country> {
        return remoteDataSource.fetchAllCountries().map { it.toDomain() }
    }

    override suspend fun getCommentsForCountry(countryName: String): List<Comment> {
        return localDataSource.getCommentsForCountry(countryName).map { it.toDomain() }
    }

    override suspend fun addComment(comment: Comment) {
        localDataSource.insertComment(comment.toEntity())
    }
}
