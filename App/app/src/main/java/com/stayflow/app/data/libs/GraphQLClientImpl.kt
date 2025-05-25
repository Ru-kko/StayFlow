package com.stayflow.app.data.libs

import com.stayflow.app.BuildConfig
import com.stayflow.app.domain.libs.GraphQLClient
import com.stayflow.app.domain.libs.GraphQLResponse
import com.stayflow.app.domain.libs.GraphqlRequest
import com.stayflow.app.domain.repository.TokenStorageRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import javax.inject.Inject

class GraphQLClientImpl @Inject constructor(
    private val http: HttpClient,
    private val tokenStorage: TokenStorageRepository
) : GraphQLClient {
    override suspend fun <T> execute(
        request: GraphqlRequest,
        deserializer: KSerializer<T>,
        includeCredentials: Boolean
    ): GraphQLResponse<T> {
        val apiUrl = BuildConfig.API_URL
        val token = tokenStorage.getToken()
        val reqBody = Json.encodeToString(request)

        val response = http.post(apiUrl) {
            contentType(ContentType.Application.Json)
            setBody(reqBody)

            if (token != null && includeCredentials) {
                headers.append("Authorization", "Bearer $token")
            }
        }

        val body = response.bodyAsText()
        return Json.decodeFromString(GraphQLResponse.serializer(deserializer), body)
    }
}