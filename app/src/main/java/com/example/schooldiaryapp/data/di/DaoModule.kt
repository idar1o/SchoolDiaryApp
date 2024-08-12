package com.example.schooldiaryapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.schooldiaryapp.data.source.local.AppDataBase
import com.example.schooldiaryapp.data.source.local.daos.AssignmentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideCharacterDao(
        appDatabase: AppDataBase,
    ): AssignmentDao = appDatabase.assignmentsDao()

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext appContext: Context,
    ): AppDataBase = Room.databaseBuilder(
        appContext,
        AppDataBase::class.java,
        AppDataBase.NAME,
    ).build()
}