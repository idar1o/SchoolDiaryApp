package com.example.schooldiaryapp.data.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.ecommerceapp.network.ApiService
import com.example.schooldiaryapp.data.ApiRepositoryImpl
import com.example.schooldiaryapp.data.LocalDateTimeAdapter
import com.example.schooldiaryapp.data.WebSocketDataSourceImpl
import com.example.schooldiaryapp.data.encryptedprefs.EncryptedPrefsHelper
import com.example.schooldiaryapp.data.source.local.daos.AssignmentDao
import com.example.schooldiaryapp.data.source.network.LocalDateAdapter
import com.example.schooldiaryapp.data.source.network.WebSocketDataSource
import com.example.schooldiaryapp.data.source.network.WebSocketRepositoryImpl
import com.example.schooldiaryapp.domain.ApiRepository
import com.example.schooldiaryapp.domain.WebSocketRepository
import com.example.schooldiaryapp.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideBaseUrl() = Constants.BASE_URL

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): ApiService {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Уровень логирования
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor) // Добавление Interceptor
            .build()
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .create()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun ApiRepository (apiService: ApiService, dao: AssignmentDao): ApiRepository = ApiRepositoryImpl(apiService, dao)


    @Provides
    @Singleton
    fun provideEncryptedPrefsHelper(@ApplicationContext context: Context): EncryptedPrefsHelper {
        return EncryptedPrefsHelper(context)
    }
    @Provides
    @Singleton
    @UserId
    fun provideUserId(encryptedPrefsHelper: EncryptedPrefsHelper): Int {
        return encryptedPrefsHelper.getLoginData()?.userId ?: -1
    }


    @Provides
    @Singleton
    @TeacherId
    fun provideTeacherId(encryptedPrefsHelper: EncryptedPrefsHelper): Int {
        return encryptedPrefsHelper.getLoginData()?.teacherId ?: -1
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Уровень логирования
        }
        return OkHttpClient.Builder()
            .addInterceptor(interceptor) // Добавление Interceptor
            .build()
    }

    @Provides
    @Singleton
    fun provideWebSocketDataSource(client: OkHttpClient): WebSocketDataSource {
        return WebSocketDataSourceImpl(client)
    }

    @Provides
    @Singleton
    fun provideWebSocketRepository(dataSource: WebSocketDataSource): WebSocketRepository {
        return WebSocketRepositoryImpl(dataSource)
    }

}
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserId
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TeacherId
