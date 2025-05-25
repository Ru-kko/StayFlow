package com.stayflow.app.domain.libs

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import kotlinx.serialization.json.JsonObject

@Serializable
data class GraphqlRequest (
    val query: String,
    val variables: JsonObject? = null
)

@Serializable
data class GraphqlError(val errors: List<ErrorItem>) : Exception("GraphQL Error")

@Serializable
@JsonIgnoreUnknownKeys
@OptIn(ExperimentalSerializationApi::class)
data class ErrorItem(
    val message: String,
    val extensions: Extensions
)

@Serializable
data class Extensions(
    val classification: String
)

@Serializable
data class GraphQLResponse<T>(
    val data: T? = null,
    val errors: List<ErrorItem>? = null
)
