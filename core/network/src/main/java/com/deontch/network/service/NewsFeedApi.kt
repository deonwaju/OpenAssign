package com.deontch.network.service

import com.deontch.network.model.NewsFeedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsFeedApi {
    @GET("latest")
    suspend fun getNewsFeed(
        @Query("language") language: String = "en",
        @Query("q") searchParam: String? = null,
        @Query("page") nextPage: String? = null,
    ): NewsFeedResponse
}