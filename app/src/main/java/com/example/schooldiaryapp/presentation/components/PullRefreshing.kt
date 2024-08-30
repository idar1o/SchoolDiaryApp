package com.example.schooldiaryapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> PullToRefreshBox(
    item: T,
    content: @Composable (T) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = onRefresh
    )

    Box(
        modifier = modifier
            .pullRefresh(pullRefreshState)
            .verticalScroll(rememberScrollState())// Убедитесь, что модификатор pullRefresh добавлен
    ) {
        // Добавляем контент с дополнительным отступом для активации свайпа

        Column(modifier = Modifier.fillMaxSize()){
            content(item)
        }


        // Добавляем индикатор обновления
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> PullToRefreshList(
    items: List<T>,
    content: @Composable (T) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState()
) {
    val pullToRefreshState = rememberPullToRefreshState()
    Box(
        modifier = Modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ){
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(items){
                content(it)
            }
        }
        if (pullToRefreshState.isRefreshing){
            LaunchedEffect(key1 = true) {
                onRefresh()
            }
        }
        LaunchedEffect(key1 = isRefreshing) {
            if(isRefreshing){
                pullToRefreshState.startRefresh()
            }else{
                pullToRefreshState.endRefresh()
            }
        }
        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
}