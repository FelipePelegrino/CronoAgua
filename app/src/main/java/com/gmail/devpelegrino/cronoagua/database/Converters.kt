package com.gmail.devpelegrino.cronoagua.database

import android.util.Log
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Converters {

    companion object TypeConverters {

        private val formatterDate = DateTimeFormatter.ISO_DATE
        private val formatterTime = DateTimeFormatter.ISO_TIME

        @TypeConverter
        @JvmStatic
        fun toLocalDate(value: String?): LocalDate? {
            return value?.let {
                Log.i("teste", "value ${value.toString()}")
                return formatterDate.parse(value, LocalDate::from)
            }
        }

        @TypeConverter
        @JvmStatic
        fun fromLocalDate(date: LocalDate?): String? {
            Log.i("teste", "date ${date.toString()}")
            return date?.format(formatterDate)
        }

        @TypeConverter
        @JvmStatic
        fun toOffsetTime(value: String?): LocalTime? {
            return value?.let {
                return formatterTime.parse(value, LocalTime::from)
            }
        }

        @TypeConverter
        @JvmStatic
        fun fromOffsetTime(time: LocalTime?): String? {
            return time?.format(formatterTime)
        }
    }
}
