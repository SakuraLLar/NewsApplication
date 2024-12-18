package dev.sakura.newsapplication

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sakura.news.database.NewsDatabase
import dev.sakura.newsapi.NewsApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providerNewsApi(): NewsApi {
        return NewsApi(
            apiKey = BuildConfig.NEWS_API_KEY,
            baseUrl = BuildConfig.NEWS_API_BASE_URL,
        )
    }

    @Provides
    @Singleton
    fun providerNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
        return NewsDatabase(context)
    }
}