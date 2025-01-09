package dev.sakura.newsapplication

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sakura.common.AppDispatchers
import dev.sakura.common.Logger
import dev.sakura.common.logcatLogger
import dev.sakura.news.database.NewsDatabase
import dev.sakura.newsapi.NewsApi
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providerNewsApi(okHttpClient: OkHttpClient?): NewsApi {
        return NewsApi(
            baseUrl = BuildConfig.NEWS_API_BASE_URL,
            apiKey = BuildConfig.NEWS_API_KEY,
            okHttpClient = okHttpClient,
        )
    }

    @Provides
    @Singleton
    fun providerNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
        return NewsDatabase(context)
    }

    @Provides
    @Singleton
    fun providerAppCoroutineDispatchers(): AppDispatchers = AppDispatchers()

    @Provides
    fun providerLogger(): Logger = logcatLogger()

}