package dev.sakura.news.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.sakura.news.database.dao.ArticleDAO
import dev.sakura.news.database.models.ArticleDBO
import dev.sakura.news.database.utils.Converters

@Database(entities = [ArticleDBO::class], version = 1)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun articlesDAO(): ArticleDAO
}

fun NewsDatabase(applicationContext: Context) : NewsDatabase {
    return Room.databaseBuilder(
        checkNotNull(applicationContext.applicationContext),
        NewsDatabase::class.java, "news"
    ).build()
}