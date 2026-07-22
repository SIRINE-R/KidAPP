package com.example.myapplication.kid_activity.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kidapp.R
import com.example.kidapp.presentation.screens.components.ActivityContainer
import com.example.kidapp.presentation.screens.components.KidWatchedVideoCard
import com.example.kidapp.presentation.screens.components.SearchContent
import com.example.kidapp.util.Routes



@Composable
fun KidActivityScreen(navController: NavHostController, email: String?, id: Int?) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Routes.ADDING.name + "/$email/$id") },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = null
                        )
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Routes.SETTINGS.name + "/$email/$id") },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null
                        )
                    }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {  },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Face,
                            contentDescription = null
                        )
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Routes.PROFILES.name + "/$email/$id") },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
            ,
            contentPadding = innerPadding
        ) {
            item {
                ActivityContainer(
                    title = stringResource(R.string.watch_history),
                    content = {
                        Column(
                            modifier = Modifier.verticalScroll(rememberScrollState())
                        ) {
                            repeat(10) {
                                KidWatchedVideoCard(
                                    title = "Cartoon",
                                    description = "See more videos on our channel and do not forget to subscribe our channel"
                                )
                            }
                        }
                    }
                )
            }
            item {
                ActivityContainer(
                    title = stringResource(R.string.search_history),
                    content = {
                        Column(
                            modifier = Modifier.verticalScroll(rememberScrollState())
                        ) {
                            repeat(10) {
                                SearchContent(
                                    text = "Cartoon",
                                    timeDate = "10:00 AM 11/23/2025"
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}
