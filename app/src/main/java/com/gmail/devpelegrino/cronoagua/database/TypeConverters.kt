package com.gmail.devpelegrino.cronoagua.database

import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.format.DateTimeFormatter

object TypeConverters {

    private val formatterDateTime = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    private val formatterTime = DateTimeFormatter.ISO_OFFSET_TIME

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            return formatterDateTime.parse(value, OffsetDateTime::from)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatterDateTime)
    }

    @TypeConverter
    @JvmStatic
    fun toOffsetTime(value: String?): OffsetTime? {
        return value?.let {
            return formatterTime.parse(value, OffsetTime::from)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetTime(date: OffsetTime?): String? {
        return date?.format(formatterTime)
    }
}