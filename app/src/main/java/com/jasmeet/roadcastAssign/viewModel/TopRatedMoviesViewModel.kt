package com.jasmeet.roadcastAssign.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.jasmeet.roadcastAssign.model.pagingSource.TopRatedMoviesPagingSource
import com.jasmeet.roadcastAssign.model.repository.TopRatedMoviesRepository
import com.jasmeet.roadcastAssign.model.topRated.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(
    private val repository: TopRatedMoviesRepository
) : ViewModel() {

    private val _topRatedMovies: MutableStateFlow<PagingData<Result>> =
        MutableStateFlow(PagingData.empty())
    val topRatedMovies = _topRatedMovies.asStateFlow()


    init {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    pageSize = 10,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = { TopRatedMoviesPagingSource(repository) }
            ).flow
                .map { pagingData ->
                    pagingData.filter { result ->
                        result.backdrop_path != null
                    }
                }
                .cachedIn(viewModelScope)
                .collectLatest {
                    _topRatedMovies.value = it
                }
        }

    }
}