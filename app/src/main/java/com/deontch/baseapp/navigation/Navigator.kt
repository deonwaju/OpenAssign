package com.deontch.baseapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.deontch.news.presentation.NewsFeedScreen
import com.deontch.news.presentation.NewsFeedScreenDestination
import com.deontch.newsdetail.presentation.NewsDetailScreen
import com.deontch.newsdetail.presentation.NewsDetailScreenDestination

@Composable
fun Navigator(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = NewsFeedScreenDestination) {
        composable<NewsFeedScreenDestination> {
            NewsFeedScreen(onNewsCardClick = {
                navController.navigate(NewsDetailScreenDestination(it))
            })
        }

        composable<NewsDetailScreenDestination> {
            val args = it.toRoute<NewsDetailScreenDestination>()
           NewsDetailScreen(args.newsId, onBackClick = { navController.popBackStack() })
        }
    }
}
