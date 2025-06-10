package com.example.proyecto_android.data.remote

import com.google.gson.annotations.SerializedName

data class CountryDto(
    @SerializedName("name") val name: NameDto,
    @SerializedName("capital") val capital: List<String>?,
    @SerializedName("flags") val flags: FlagDto,
    @SerializedName("languages") val languages: Map<String, String>?,
    @SerializedName("population") val population: Long
)

data class NameDto(
    @SerializedName("common") val common: String
)

data class FlagDto(
    @SerializedName("png") val png: String
)
