package com.jasmeet.roadcastAssign.view.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.jasmeet.roadcastAssign.utils.ImgSize
import com.jasmeet.roadcastAssign.utils.Utils
import com.jasmeet.roadcastAssign.view.theme.sans
import com.jasmeet.roadcastAssign.viewModel.TopRatedMoviesViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    navController: NavHostController,
    topRatedMoviesViewModel: TopRatedMoviesViewModel = hiltViewModel()
) {
    val topRatedMovieResponseState =
        topRatedMoviesViewModel.topRatedMovies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Movie List")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)

        ) {
            items(
                topRatedMovieResponseState.itemCount,
                key = { key ->
                    key.toString()
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
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .graphicsLayer {
                            this.scaleX = animatable.value
                            this.scaleY = animatable.value
                        }
                        .height(LocalConfiguration.current.screenHeightDp.dp * 0.12f)
                        .fillMaxWidth()
                        .clickable {
                            topRatedMovieResponseState[index]?.overview

                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center


                ) {
                    SubcomposeAsyncImage(
                        model = url,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .fillMaxHeight()
                            .fillMaxWidth(0.35f),
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
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = topRatedMovieResponseState[index]?.title ?: "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            color = Color.White,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = sans
                        )
                        Text(
                            text = topRatedMovieResponseState[index]?.overview?.replace(
                                "\\s+".toRegex(),
                                " "
                            ) ?: "",
                            modifier = Modifier
                                .padding(horizontal = 5.dp)
                                .fillMaxWidth(),
                            color = Color.White,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = sans
                        )
                    }

                }
                HorizontalDivider(Modifier.padding(horizontal = 5.dp))

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


}
