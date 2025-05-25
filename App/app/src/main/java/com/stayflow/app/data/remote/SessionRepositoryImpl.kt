package com.stayflow.app.data.remote

import com.stayflow.app.domain.libs.GraphQLClient
import com.stayflow.app.domain.libs.GraphqlError
import com.stayflow.app.domain.libs.GraphqlRequest
import com.stayflow.app.domain.model.UserRegister
import com.stayflow.app.domain.model.remote.TokenResponse
import com.stayflow.app.domain.repository.SessionRepository
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonObject
import javax.inject.Inject

@Serializable
private data class LoginUserDataWrapper(val loginUser: TokenResponse)
@Serializable
private data class RegisterUserDataWrapper(val registerUser: TokenResponse)

class SessionRepositoryImpl @Inject constructor(private val gql: GraphQLClient) : SessionRepository {
    override suspend fun login(email: String, password: String): TokenResponse {
        val query = """
            mutation LoginUser(${'$'}creds: UserLogin!) {
              loginUser(creds: ${'$'}creds) {
                token
                expiresIn
                issuedAt
              }
            }
        """.trimIndent()

        val variables = buildJsonObject {
            putJsonObject("creds") {
                put("email", email)
                put("password", password)
            }
        }

        val req = GraphqlRequest(query, variables)
        val response = gql.execute(
            req,
            LoginUserDataWrapper.serializer(),
            false
        )

        if (!response.errors.isNullOrEmpty()) {
            throw GraphqlError(response.errors)
        }

        return response.data?.loginUser ?: throw NullPointerException("Login failed")
    }

    override suspend fun register(user: UserRegister): TokenResponse {
        val query = """
            mutation RegisterUser(${'$'}user: UserRegister!) {
              registerUser(user: ${'$'}user) {
                token
                expiresIn
                issuedAt
              }
            }
        """.trimIndent()

        val variables = buildJsonObject {
            putJsonObject("user") {
                put("email", user.email)
                put("firstName", user.firstName)
                put("lastName", user.lastName)
                put("password", user.password)
            }
        }

        val req = GraphqlRequest(query, variables)
        val response = gql.execute(
            req,
            RegisterUserDataWrapper.serializer(),
            false
        )

        if (!response.errors.isNullOrEmpty()) {
            throw GraphqlError(response.errors)
        }

        return response.data?.registerUser ?: throw NullPointerException("Register failed")
    }
}