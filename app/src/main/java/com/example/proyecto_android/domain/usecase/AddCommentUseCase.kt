package com.example.proyecto_android.domain.usecase

import com.example.proyecto_android.domain.model.Comment
import com.example.proyecto_android.domain.repository.CountryRepository
import javax.inject.Inject

class AddCommentUseCase @Inject constructor(
    private val repository: CountryRepository
) {
    suspend operator fun invoke(comment: Comment) {
        repository.addComment(comment)
    }
}
