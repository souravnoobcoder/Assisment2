package com.example.assisment2.dI

import android.app.Application
import androidx.room.Room
import com.example.assisment2.api.CocktailApi
import com.example.assisment2.room.CocktailDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(CocktailApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideCocktailApi(retrofit: Retrofit): CocktailApi =
        retrofit.create(CocktailApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application) =
        Room.databaseBuilder(app,CocktailDatabase::class.java,"cocktailDatabased")
            .build()
}