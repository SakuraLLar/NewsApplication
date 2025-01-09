package dev.sakura.newsapplication

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BuildModule {

    @Provides
    @Singleton
    fun providerHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

}