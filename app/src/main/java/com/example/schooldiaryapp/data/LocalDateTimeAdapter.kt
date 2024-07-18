package com.example.schooldiaryapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class LocalDateTimeAdapter : JsonDeserializer<LocalDateTime> {

    @RequiresApi(Build.VERSION_CODES.O)
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime {
        val dateString = json.asString
        return try {
            // Парсим строку в Instant
            val instant = Instant.parse(dateString)
            // Преобразуем Instant в LocalDateTime с использованием зоны UTC или другой зоны
            LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
        } catch (e: Exception) {
            // Обрабатываем исключение при парсинге
            throw JsonParseException("Invalid date format: $dateString", e)
        }
    }
}
