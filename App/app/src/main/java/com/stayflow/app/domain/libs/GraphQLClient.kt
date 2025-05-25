package com.stayflow.app.domain.libs

import kotlinx.serialization.KSerializer

interface GraphQLClient {
    suspend fun <T> execute(
        request: GraphqlRequest,
        deserializer: KSerializer<T>,
        includeCredentials: Boolean = true
    ): GraphQLResponse<T>
}