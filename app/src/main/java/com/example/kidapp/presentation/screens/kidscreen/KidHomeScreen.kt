package com.example.kidapp.presentation.screens.kidscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.window.core.layout.WindowWidthSizeClass
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

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isCompact = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT

    LaunchedEffect(Unit) {
        kidHomeViewModel.getListOfVideos()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = if (isCompact) 16.dp else 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            SearchBar(
                value = "",
                onValueChange = { },
                painter = kidHomeViewModel.getDrawableRes(iconProfile),
                onClick = {
                    navController.navigate(Routes.SEARCH.name + "/$iconProfile" + "/$childId")
                },
                isReadOnly = true,
                enabled = false,
                modifier = Modifier.widthIn(max = 600.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is KidHomeUIState.Error -> {
                    Text(
                        text = state.message,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                is KidHomeUIState.Loading -> {
                    CircularProgressIndicator()
                }

                is KidHomeUIState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 180.dp),
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(
                            items = videos,
                            key = { video -> video.video_id }
                        ) { video ->
                            VideoCard(
                                title = video.title,
                                description = video.description,
                                thumbnail = video.thumbnail_url
                            ) {
                                val encodedTitle = URLEncoder.encode(
                                    video.title,
                                    StandardCharsets.UTF_8.toString()
                                )
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
}
