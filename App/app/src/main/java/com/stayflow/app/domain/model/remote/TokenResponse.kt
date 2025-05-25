package com.stayflow.app.domain.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse (
    val token: String,
    val expiresIn: Long,
    val issuedAt: Long,
)