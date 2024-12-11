package dev.sakura.news.database.utils

import androidx.room.TypeConverter
import java.text.DateFormat
import java.util.Date

internal class Converters {

    @TypeConverter
    fun fromTimestamp(value: String?): Date? {
        return value?.let { DateFormat.getDateTimeInstance().parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(data: Date?): String? {
        return data?.time?.let { DateFormat.getDateTimeInstance().format(it) }
    }
}