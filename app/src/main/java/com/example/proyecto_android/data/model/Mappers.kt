package com.example.proyecto_android.data.model

import com.example.proyecto_android.data.local.CommentEntity
import com.example.proyecto_android.data.remote.CountryDto
import com.example.proyecto_android.domain.model.Comment
import com.example.proyecto_android.domain.model.Country

// CountryDto -> Country (Dominio)
fun CountryDto.toDomain(): Country {
    return Country(
        name = name.common,
        capital = capital?.firstOrNull() ?: "N/A",
        flagUrl = flags.png,
        languages = languages?.values?.toList() ?: emptyList(),
        population = population
    )
}

// CommentEntity -> Comment (Dominio)
fun CommentEntity.toDomain(): Comment {
    return Comment(
        id = id,
        countryName = countryName,
        content = content
    )
}

// Comment -> CommentEntity (para insertar en Room)
fun Comment.toEntity(): CommentEntity {
    return CommentEntity(
        id = id,
        countryName = countryName,
        content = content
    )
}
