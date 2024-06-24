package com.botsheloramela.informedeye.data.remote

import com.botsheloramela.informedeye.data.remote.dto.NewsResponse
import com.botsheloramela.informedeye.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = API_KEY
    ) : NewsResponse

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("page") page: Int,
        @Query("country") country: String = "za",
        @Query("apiKey") apiKey: String = API_KEY
    ) : NewsResponse

}