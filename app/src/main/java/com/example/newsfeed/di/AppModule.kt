package com.example.newsfeed.di

import android.app.Application
import com.example.newsfeed.data.dataSource.ApiService
import com.example.newsfeed.data.repository.NewsRepositoryImplementation
import com.example.newsfeed.domain.repository.NewsRepository
import com.example.newsfeed.core.AppDispatcher
import com.example.newsfeed.utils.Constants
import com.example.newsfeed.core.Dispatcher
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(application: Application): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val cacheDir = File(application.cacheDir, UUID.randomUUID().toString())

        val cache = Cache(cacheDir, 2 * 1024 * 1024)

        return OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApiService(gson: Gson, okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: ApiService): NewsRepository {
        return NewsRepositoryImplementation(api)
    }

    @Provides
    @Singleton
    fun provideAppDispatcher(): Dispatcher {
        return AppDispatcher()
    }
}