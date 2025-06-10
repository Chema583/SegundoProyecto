package com.example.proyecto_android.di

import android.content.Context
import androidx.room.Room
import com.example.proyecto_android.data.local.AppDatabase
import com.example.proyecto_android.data.local.CommentDao
import com.example.proyecto_android.data.local.LocalDataSource
import com.example.proyecto_android.data.remote.CountryApiService
import com.example.proyecto_android.data.remote.RemoteDataSource
import com.example.proyecto_android.data.repository.CountryRepositoryImpl
import com.example.proyecto_android.domain.repository.CountryRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://restcountries.com/"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "countries_db"
        ).build()
    }

    @Provides
    fun provideCommentDao(db: AppDatabase): CommentDao = db.commentDao()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideCountryApiService(retrofit: Retrofit): CountryApiService {
        return retrofit.create(CountryApiService::class.java)
    }

    @Provides
    fun provideRemoteDataSource(api: CountryApiService): RemoteDataSource {
        return RemoteDataSource(api)
    }

    @Provides
    fun provideLocalDataSource(dao: CommentDao): LocalDataSource {
        return LocalDataSource(dao)
    }

    @Provides
    @Singleton
    fun provideCountryRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): CountryRepository {
        return CountryRepositoryImpl(remoteDataSource, localDataSource)
    }
}
