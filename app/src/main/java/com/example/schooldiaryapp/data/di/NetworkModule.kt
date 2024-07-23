package com.example.schooldiaryapp.data.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.ecommerceapp.network.ApiService
import com.example.schooldiaryapp.data.ApiRepositoryImpl
import com.example.schooldiaryapp.data.LocalDateTimeAdapter
import com.example.schooldiaryapp.domain.ApiRepository
import com.example.schooldiaryapp.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
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
     fun ApiRepository (apiService: ApiService): ApiRepository = ApiRepositoryImpl(apiService)

}
