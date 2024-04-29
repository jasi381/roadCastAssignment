package com.jasmeet.roadcastAssign.view.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jasmeet.roadcastAssign.view.screens.LocationScreen
import com.jasmeet.roadcastAssign.view.screens.MovieListScreen
import com.jasmeet.roadcastAssign.view.screens.OptionsScreen
import com.jasmeet.roadcastAssign.view.screens.Screens
import com.jasmeet.roadcastAssign.view.screens.SplashScreen

@Composable
fun RoadCastNavigator(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route
    ) {
        composable(
            route = Screens.SplashScreen.route,
            enterTransition = {
                enterTransition()
            },
            exitTransition = {
                exitTransition()
            },
            popEnterTransition = {
                enterTransition()
            },
            popExitTransition = {
                exitTransition()
            }

        ) {
            SplashScreen(navController = navController)
        }

        composable(
            route = Screens.OptionsScreen.route,
            enterTransition = {
                enterTransition()
            },
            exitTransition = {
                exitTransition()
            },
            popEnterTransition = {
                enterTransition()
            },
            popExitTransition = {
                exitTransition()
            }

        ) {
            OptionsScreen(navController = navController)
        }

        composable(
            route = Screens.LocationScreen.route,
            enterTransition = {
                enterTransition()
            },
            exitTransition = {
                exitTransition()
            },
            popEnterTransition = {
                enterTransition()
            },
            popExitTransition = {
                exitTransition()
            }

        ) {
            LocationScreen(navController = navController)
        }

        composable(
            route = Screens.MovieListScreen.route,
            enterTransition = {
                enterTransition()
            },
            exitTransition = {
                exitTransition()
            },
            popEnterTransition = {
                enterTransition()
            },
            popExitTransition = {
                exitTransition()
            }

        ) {
            MovieListScreen(navController = navController)
        }

    }

}


private fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition() =
    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(800))


private fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition() =
    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(800))