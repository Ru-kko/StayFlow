package com.stayflow.app.di

import com.stayflow.app.data.libs.GraphQLClientImpl
import com.stayflow.app.domain.libs.GraphQLClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HttpClientProvider {
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            expectSuccess = true
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                })
            }
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface GraphQLClientModule {
    @Binds
    @Singleton
    fun bindGraphqlClient(impl: GraphQLClientImpl): GraphQLClient
}

