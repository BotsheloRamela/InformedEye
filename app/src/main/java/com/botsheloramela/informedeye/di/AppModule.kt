package com.botsheloramela.informedeye.di

import com.botsheloramela.informedeye.data.remote.NewsApi
import com.botsheloramela.informedeye.data.repository.NewsRepositoryImpl
import com.botsheloramela.informedeye.domain.repository.NewsRepository
import com.botsheloramela.informedeye.domain.usecase.GetNewsUseCase
import com.botsheloramela.informedeye.domain.usecase.GetTopHeadlinesUseCase
import com.botsheloramela.informedeye.domain.usecase.NewsUseCases
import com.botsheloramela.informedeye.utils.Constants.BASE_URL
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
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(
        newsApi: NewsApi
    ): NewsRepository = NewsRepositoryImpl(newsApi)

    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository
    ): NewsUseCases {
        return NewsUseCases(
            getNews = GetNewsUseCase(newsRepository),
            getTopHeadlines = GetTopHeadlinesUseCase(newsRepository)
        )
    }
}