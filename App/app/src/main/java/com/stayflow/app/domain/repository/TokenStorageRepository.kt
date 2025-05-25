package com.stayflow.app.domain.repository

interface TokenStorageRepository {
    fun saveToken(token: String, expires: Long)
    fun getToken(): String?
    fun clear()
}