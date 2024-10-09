package com.deontch.network

import com.deontch.network.model.NewsFeedResponse
import com.deontch.network.service.NewsFeedApi
import io.mockk.coEvery
import io.mockk.mockk

class MockNewsFeedApi {
    val api: NewsFeedApi = mockk()

    fun setupMockGetNewsFeed(response: NewsFeedResponse) {
        coEvery { api.getNewsFeed(any(), any(), any()) } returns response
    }
}