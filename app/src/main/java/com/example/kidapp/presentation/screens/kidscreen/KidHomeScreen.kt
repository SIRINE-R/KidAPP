package com.example.kidapp.presentation.screens.kidscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.kidapp.presentation.screens.components.SearchBar
import com.example.kidapp.presentation.screens.components.VideoCard
import com.example.kidapp.util.Routes
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun KidHomeScreen(
    navController: NavHostController,
    iconProfile: String?,
    childId: Int?,
    kidHomeViewModel: KidHomeScreenViewModel = hiltViewModel()
) {
    val videos by kidHomeViewModel.videos.collectAsState()
    val uiState by kidHomeViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        kidHomeViewModel.getListOfVideos()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            value = "",
            onValueChange = {  },
            painter = kidHomeViewModel.getDrawableRes(iconProfile),
            onClick = {
                navController.navigate(Routes.SEARCH.name+"/$iconProfile"+"/$childId")
            },
            isReadOnly = true,
            enabled = false,
        )
        when(uiState) {
            is KidHomeUIState.Error -> {
                val errorMessage = (uiState as KidHomeUIState.Error).message
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = typography.bodySmall,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            is KidHomeUIState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            is KidHomeUIState.Success -> {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(8.dp)
                ) {
                    items(videos) { video ->
                        VideoCard(
                            title = video.title,
                            description = video.description,
                            thumbnail = video.thumbnail_url
                        ) {
                            val encodedTitle =
                                URLEncoder.encode(video.title, StandardCharsets.UTF_8.toString())
                            navController.navigate(
                                Routes.PLAYERSCREEN.name + "/${video.video_id}/$encodedTitle/${childId}"
                            )
                        }
                    }
                }
            }

            else -> {}
        }
        }
    }

