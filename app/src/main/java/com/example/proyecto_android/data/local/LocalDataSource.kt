package com.example.proyecto_android.data.local

import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val commentDao: CommentDao
) {
    suspend fun getCommentsForCountry(countryName: String): List<CommentEntity> {
        return commentDao.getCommentsByCountry(countryName)
    }

    suspend fun insertComment(comment: CommentEntity) {
        commentDao.insertComment(comment)
    }
}
