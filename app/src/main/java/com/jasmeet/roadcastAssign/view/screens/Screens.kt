package com.jasmeet.roadcastAssign.view.screens


sealed class Screens(val route: String) {

    data object SplashScreen : Screens("splash")
    data object OptionsScreen : Screens("options")
    data object LocationScreen : Screens("location")
    data object MovieListScreen : Screens("movies")
}