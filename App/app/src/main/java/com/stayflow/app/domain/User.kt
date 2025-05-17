package com.stayflow.app.domain

import java.util.UUID

data class User (
    val userId: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: Role
)

sealed class Role(val name: String) {
    data object USER: Role("User")
    data object ADMIN: Role("Administrator")
    data object OWNER: Role("Owner")
}


data class FullUser (
    val userId: UUID,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: Role,
    val password: String,
)
