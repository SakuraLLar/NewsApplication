package dev.sakura.news.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.sakura.news.database.dao.ArticleDAO
import dev.sakura.news.database.models.ArticleDBO
import dev.sakura.news.database.utils.Converters

class NewsDatabase internal constructor(private val database: NewsRoomDatabase) {

    val articleDAO: ArticleDAO
        get() = database.articlesDAO()
}

@Database(entities = [ArticleDBO::class], version = 1)
@TypeConverters(Converters::class)
internal abstract class NewsRoomDatabase : RoomDatabase() {

    abstract fun articlesDAO(): ArticleDAO
}

fun NewsDatabase(applicationContext: Context) : NewsDatabase {
    val newsRoomDatabase = Room.databaseBuilder(
        checkNotNull(applicationContext.applicationContext),
        NewsRoomDatabase::class.java, "news"
    ).build()
    return NewsDatabase(newsRoomDatabase)
}