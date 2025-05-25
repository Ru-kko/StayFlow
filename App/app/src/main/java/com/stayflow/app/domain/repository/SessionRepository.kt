package com.stayflow.app.domain.repository

import com.stayflow.app.domain.model.UserRegister
import com.stayflow.app.domain.model.remote.TokenResponse

interface SessionRepository {
    suspend fun login(email: String, password: String): TokenResponse
    suspend fun register(user: UserRegister): TokenResponse
}