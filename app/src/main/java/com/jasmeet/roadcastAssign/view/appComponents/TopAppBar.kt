package com.jasmeet.roadcastAssign.view.appComponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import com.jasmeet.roadcastAssign.view.theme.sans


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComponent(
    title: String,
    enableBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    backIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    enableAction: Boolean = false,
    onActionClick: () -> Unit = {},
    actionIcon: ImageVector = Icons.Default.MoreVert,
    fontFamily: FontFamily = sans
) {

    CenterAlignedTopAppBar(

        title = {
            Text(
                text = title,
                fontFamily = fontFamily,
            )
        },
        navigationIcon = {
            if (enableBackButton) {
                IconButton(
                    onClick = {
                        onBackClick.invoke()
                    }
                ) {
                    Icon(
                        imageVector = backIcon,
                        contentDescription = null
                    )
                }
            }
        },
        actions = {
            if (enableAction) {
                IconButton(
                    onClick = { onActionClick.invoke() }

                ) {

                    Icon(
                        imageVector = actionIcon,
                        contentDescription = null,
                    )

                }

            }
        }
    )

}

