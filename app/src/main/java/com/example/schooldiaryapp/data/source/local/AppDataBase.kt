package com.example.schooldiaryapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.schooldiaryapp.data.source.local.AppDataBase.Companion.DB_VERSION
import com.example.schooldiaryapp.data.source.local.daos.AssignmentDao
import com.example.schooldiaryapp.data.source.local.models.CachedAssignments

@Database(
    entities = [
        CachedAssignments::class
    ],
    version = DB_VERSION,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDataBase: RoomDatabase() {

    companion object {
        const val DB_VERSION = 1
        const val NAME = "app.db"
    }
    abstract fun assignmentsDao(): AssignmentDao
}