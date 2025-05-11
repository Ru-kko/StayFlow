package com.stayflow.app.di

import android.content.Context
import coil3.ImageLoader
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageCacheModule {
    @Provides
    @Singleton
    fun provideMemoryCache(@ApplicationContext context: Context): MemoryCache {
        return MemoryCache.Builder()
            .maxSizePercent(context, 0.2)
            .build()
    }

    @Provides
    @Singleton
    fun provideImageCache(@ApplicationContext context: Context, memoryCache: MemoryCache): ImageLoader {
        return ImageLoader.Builder(context)
            .crossfade(true)
            .memoryCache(memoryCache)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()
    }
}