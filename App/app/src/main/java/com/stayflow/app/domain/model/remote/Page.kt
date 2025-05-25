package com.stayflow.app.domain.model.remote

data class Page<T> (
    val content: List<T>,
    val totalItems: Long,
    val totalPages: Int,
    val actualPage: Int,
)