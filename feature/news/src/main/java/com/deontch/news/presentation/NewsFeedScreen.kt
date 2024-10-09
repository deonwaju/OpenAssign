package com.deontch.news.presentation

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.deontch.base.contract.ViewEvent
import com.deontch.base.model.MessageState
import com.deontch.common.Ignored
import com.deontch.common.displayToast
import com.deontch.feature.news.R
import com.deontch.news.domain.contract.NewsFeedSideEffect
import com.deontch.news.domain.contract.NewsFeedViewAction
import com.deontch.news.presentation.component.AnimatedSearchBarComponent
import com.deontch.news.presentation.component.ArticleCard
import com.deontch.news.presentation.component.ToolBarActionComponents
import com.deontch.newsdetail.presentation.components.CenteredText
import com.deontch.ui.components.BaseScreen
import com.deontch.ui.components.CenteredColumn
import com.deontch.ui.components.InfiniteListHandler
import com.deontch.ui.components.LoadingAnimation
import com.deontch.ui.components.ToolBarTitleComponent
import com.deontch.ui.extensions.collectAllEffect
import com.deontch.ui.extensions.rememberStateWithLifecycle
import com.deontch.ui.theme.Dimens.ExtraSmallPadding2
import com.deontch.ui.theme.Dimens.MediumPadding1
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

@Serializable
object NewsFeedScreenDestination

@Composable
fun NewsFeedScreen(onNewsCardClick: (String) -> Unit) {

    val viewModel: NewsFeedViewModel = hiltViewModel()
    val state by rememberStateWithLifecycle(viewModel.state)
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val errorMessage = state.errorState as? MessageState.Inline
    val activity = context as? Activity

    SubscribeToSideEffects(
        events = viewModel.events, context = context
    )

    LaunchedEffect(Unit) {
        viewModel.onViewAction(NewsFeedViewAction.GetNewsFeed)
    }

    BaseScreen(
        state = state,
        topAppBarContent = { ToolBarTitleComponent(text = stringResource(R.string.news_feed)) },
        onSystemBackClick = {
            activity?.finishAffinity()
        },
    ) {
        Column {
            if (state.newsFeedList.isEmpty() && !state.isLoading) {
                CenteredText(
                    buttonText = stringResource(R.string.retry),
                    text = errorMessage?.message.orEmpty(),
                    onCenteredTextAction = {
                        viewModel.onViewAction(NewsFeedViewAction.GetNewsFeed)
                    }
                )
            } else if (
                state.searchQueryResponse.isEmpty() &&
                !state.isLoading &&
                state.searchQuery.isNotEmpty()
            ) {
                if (state.searchQuery.isNotEmpty()) {
                    CenteredText(
                        buttonText = stringResource(R.string.reload_news),
                        text = stringResource(
                            R.string.no_result_matches_your_search_query,
                            state.searchQuery
                        ),
                        onCenteredTextAction = {
                            viewModel.onViewAction(NewsFeedViewAction.UpdateSearchQuery(""))
                            viewModel.onViewAction(NewsFeedViewAction.GetNewsFeed)
                        })
                }
            } else {
                InfiniteListHandler(listState = listState, loadMore = {
                    viewModel.onViewAction(NewsFeedViewAction.LoadMoreNews)
                })

                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(MediumPadding1),
                    contentPadding = PaddingValues(all = ExtraSmallPadding2)
                ) {
                    items(state.newsFeedList, key = { it.articleId }) { article ->
                        ArticleCard(
                            article = article, onClick = onNewsCardClick
                        )
                    }
                    if (state.isLoadingMore) {
                        item {
                            CenteredColumn(
                                modifier = Modifier.height(100.dp)
                            ) {
                                LoadingAnimation(
                                    circleSize = 10.dp,
                                    travelDistance = 20.dp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun SubscribeToSideEffects(
    events: Flow<ViewEvent>, context: Context
) {
    LaunchedEffect(events) {
        events.collectAllEffect<NewsFeedSideEffect> { effect ->
            when (effect) {
                is NewsFeedSideEffect.ShowErrorMessage -> {
                    when (effect.errorMessageState) {
                        is MessageState.Toast -> {
                            displayToast(context, effect.errorMessageState.message)
                        }

                        else -> Ignored
                    }
                }
            }
        }
    }
}