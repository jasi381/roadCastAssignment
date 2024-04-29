package com.jasmeet.roadcastAssign.view.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.jasmeet.roadcastAssign.utils.ImgSize
import com.jasmeet.roadcastAssign.utils.Utils
import com.jasmeet.roadcastAssign.viewModel.TopRatedMoviesViewModel


@Composable
fun MovieListScreen(
    navController: NavHostController,
    topRatedMoviesViewModel: TopRatedMoviesViewModel = hiltViewModel()
) {
    val topRatedMovieResponseState =
        topRatedMoviesViewModel.topRatedMovies.collectAsLazyPagingItems()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),

        ) {
        items(
            topRatedMovieResponseState.itemCount,
            key = {
                it.toString()
            }
        ) { index ->
            val url = Utils.getImageLinkWithSize(
                topRatedMovieResponseState[index]?.backdrop_path,
                ImgSize.Original
            )

            val animatable = remember {
                Animatable(0.7f)
            }

            LaunchedEffect(key1 = true) {
                animatable.animateTo(
                    1f,
                    tween(350, delayMillis = 100, easing = FastOutSlowInEasing)
                )

            }
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        this.scaleX = animatable.value
                        this.scaleY = animatable.value
                    }
                    .height(LocalConfiguration.current.screenHeightDp.dp * 0.19f)
                    .width(LocalConfiguration.current.screenWidthDp.dp * 0.6f)
                    .clip(MaterialTheme.shapes.large)


            ) {
                SubcomposeAsyncImage(
                    model = url,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    loading = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                color = Color(0xffE50914),
                                strokeWidth = 3.dp,
                                strokeCap = StrokeCap.Round,

                                )
                        }
                    }
                )
                Text(
                    text = topRatedMovieResponseState[index]?.title ?: "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(Color.Black.copy(alpha = 0.3f))
                        .padding(5.dp),
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

            }

        }
        topRatedMovieResponseState.apply {
            when {
                loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }

                loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .statusBarsPadding()
                                .navigationBarsPadding()
                        ) {
                            Text(
                                text = "Error",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }

    }


}
