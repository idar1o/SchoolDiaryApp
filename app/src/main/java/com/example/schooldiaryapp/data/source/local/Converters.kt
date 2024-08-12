package com.example.schooldiaryapp.data.source.local

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Converters {

    // Converter for LocalDate
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toLocalDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE)
    }

    // Converter for List<Int>
    @TypeConverter
    fun fromIntList(intList: List<Int>): String {
        return intList.joinToString(separator = ",")
    }

    @TypeConverter
    fun toIntList(data: String): List<Int> {
        return data.split(",").map { it.toInt() }
    }

    // Converter for List<String>
    @TypeConverter
    fun fromStringList(stringList: List<String>): String {
        return stringList.joinToString(separator = ",")
    }

    @TypeConverter
    fun toStringList(data: String): List<String> {
        return data.split(",")
    }
}
