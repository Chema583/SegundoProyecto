package com.example.proyecto_android.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_android.domain.model.Comment
import com.example.proyecto_android.domain.usecase.AddCommentUseCase
import com.example.proyecto_android.domain.usecase.GetCommentsForCountryUseCase
import com.example.proyecto_android.domain.usecase.GetAllCountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCommentsForCountryUseCase: GetCommentsForCountryUseCase,
    private val addCommentUseCase: AddCommentUseCase,
    private val getAllCountriesUseCase: GetAllCountriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state

    fun loadCountryDetails(countryName: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val allCountries = getAllCountriesUseCase()
                val country = allCountries.find { it.name == countryName }
                val comments = getCommentsForCountryUseCase(countryName)

                _state.update {
                    it.copy(
                        country = country,
                        comments = comments,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun addComment(countryName: String, content: String) {
        viewModelScope.launch {
            addCommentUseCase(Comment(countryName = countryName, content = content))
            val updatedComments = getCommentsForCountryUseCase(countryName)
            _state.update { it.copy(comments = updatedComments) }
        }
    }
}
