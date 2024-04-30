package com.jasmeet.roadcastAssign.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavHostController
import com.jasmeet.roadcastAssign.view.appComponents.HorizontalDividerComponent
import com.jasmeet.roadcastAssign.view.appComponents.TextComponent
import com.jasmeet.roadcastAssign.view.appComponents.TopAppBarComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionsScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBarComponent(title = "Select Options")
        }
    ) {
        Column(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(it)
        ) {
            TextButton(
                shape = RectangleShape,
                onClick = dropUnlessResumed {
                    navController.navigate(Screens.LocationScreen.route)

                }
            ) {
                TextComponent(
                    text = "Get Your Location Details",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            HorizontalDividerComponent(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onBackground
            )

            TextButton(
                shape = RectangleShape,
                onClick = dropUnlessResumed {
                    navController.navigate(Screens.MovieListScreen.route)
                }
            ) {
                TextComponent(
                    text = "Get Top-Rated Movies",
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            HorizontalDividerComponent(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onBackground
            )

        }
    }

}



