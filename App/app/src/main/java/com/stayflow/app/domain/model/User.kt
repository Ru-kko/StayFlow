package com.stayflow.app.domain.model

import com.stayflow.app.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class User (
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: Role
)

@Serializable
sealed class Role(val name: String) {
    data object USER: Role("User")
    data object ADMIN: Role("Administrator")
    data object OWNER: Role("Owner")
}


@Serializable
data class UserRegister (
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
)
