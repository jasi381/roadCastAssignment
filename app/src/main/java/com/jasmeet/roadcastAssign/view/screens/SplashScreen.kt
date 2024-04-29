package com.jasmeet.roadcastAssign.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.jasmeet.roadcastAssign.R
import com.jasmeet.roadcastAssign.view.theme.sans
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    LaunchedEffect(key1 = Unit) {
        delay(2000)
        val navOptions = NavOptions.Builder()
            .setPopUpTo(Screens.SplashScreen.route, inclusive = true)
            .build()
        navController.navigate(
            Screens.OptionsScreen.route, navOptions
        )

    }

    Column(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xff46a2d2),
                        Color(0xff44a1d1),
                        Color(0xff59aad5)
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "The Most Advanced AI Powered Digital\nLogistics Platform",
            fontFamily = sans,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.5.sp,
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 18.dp, start = 4.dp, end = 4.dp)
        )

        Text(
            text = "Because every mile counts.",
            fontFamily = sans,
            color = Color.White,
            fontWeight = FontWeight.Normal,
            fontSize = 16.5.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(
                    start = 4.dp, end = 4.dp,
                    top = 8.dp,
                    bottom = 20.dp
                )
        )

        Image(
            painter = painterResource(id = R.drawable.ic_banner),
            contentDescription = null,
            modifier = Modifier.padding(top = 20.dp)
        )


        Row(
            Modifier
                .padding(top = 35.dp)
                .fillMaxWidth()
                .align(Alignment.End)
                .navigationBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "RoadCast",
                fontFamily = sans,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 22.5.sp,
                modifier = Modifier,

                )
        }
        CircularProgressIndicator()

    }


}







