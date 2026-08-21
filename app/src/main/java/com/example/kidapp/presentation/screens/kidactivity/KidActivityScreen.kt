package com.example.kidapp.presentation.screens.kidactivity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.kidapp.R
import com.example.kidapp.domain.model.SearchTexts
import com.example.kidapp.domain.model.Video
import com.example.kidapp.presentation.screens.components.ActivityContainer
import com.example.kidapp.presentation.screens.components.AppNavigationSuite
import com.example.kidapp.presentation.screens.components.KidWatchedVideoCard
import com.example.kidapp.presentation.screens.components.SearchContent
import com.example.kidapp.util.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KidActivityScreen(
    navController: NavHostController,
    email: String?,
    id: Int?,
    viewModel: KidActivityViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = id) {
        viewModel.getChildren(id)
    }

    val children by viewModel.child.collectAsState()
    val isExpanded by viewModel.isExpanded.collectAsState()
    val selectedName by viewModel.selectedName.collectAsState()
    val selectedChildId by viewModel.selectedChildId.collectAsState()
    val videos by viewModel.video.collectAsState()
    val searchTexts by viewModel.searchedTexts.collectAsState()

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isCompact = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT

    AppNavigationSuite(
        isHomeSelected = false,
        isSettingsSelected = false,
        isActivitySelected = true,
        isAddSelected = false,
        onHomeClick = { navController.navigate(Routes.PROFILES.name + "/$email/$id") },
        onSettingsClick = { navController.navigate(Routes.SETTINGS.name + "/$email/$id") },
        onActivityClick = { },
        onAddClick = { navController.navigate(Routes.ADDING.name + "/$email/$id") },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = { viewModel.onExpandClick() },
                    modifier = Modifier.widthIn(max = 400.dp)
                ) {
                    TextField(
                        modifier = Modifier
                            .menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true)
                            .fillMaxWidth(),
                        readOnly = true,
                        value = selectedName,
                        onValueChange = {},
                        label = { Text(text = stringResource(R.string.choose_kid_s_name)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) }
                    )
                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { viewModel.onExpandClick() }
                    ) {
                        children.forEach { child ->
                            DropdownMenuItem(
                                text = { Text(child.name) },
                                onClick = {
                                    viewModel.onSelectedNameChange(child.name)
                                    viewModel.onSelectedChildIdChange(child.id)
                                    viewModel.onExpandClick()
                                    viewModel.getWatchedVideosAndSearchTexts(child.id, child.name)
                                }
                            )
                        }
                    }
                }

                if (isCompact) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ActivityContainer(
                            title = stringResource(R.string.watch_history),
                            content = {
                                WatchHistoryList(
                                    videos = videos,
                                    selectedChildId = selectedChildId,
                                    navController = navController
                                )
                            }
                        )
                        ActivityContainer(
                            title = stringResource(R.string.search_history),
                            content = {
                                SearchHistoryList(searchTexts = searchTexts)
                            }
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            ActivityContainer(
                                title = stringResource(R.string.watch_history),
                                content = {
                                    WatchHistoryList(
                                        videos = videos,
                                        selectedChildId = selectedChildId,
                                        navController = navController
                                    )
                                }
                            )
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            ActivityContainer(
                                title = stringResource(R.string.search_history),
                                content = {
                                    SearchHistoryList(searchTexts = searchTexts)
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun WatchHistoryList(
    videos: List<Video>,
    selectedChildId: Int?,
    navController: NavHostController
) {
    val scrollState1 = rememberScrollState()
    if (videos.isEmpty()) {
        EmptyHistoryMessage()
    } else {
        Column(
            modifier = Modifier.fillMaxWidth().verticalScroll(scrollState1),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            videos.forEach { video ->
                KidWatchedVideoCard(
                    title = video.title,
                    description = video.description,
                    thumbnail = video.thumbnail_url,
                    onClick = {
                        navController.navigate(
                            Routes.PLAYERSCREEN.name + "/${video.video_id}/watched/${selectedChildId}"
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun SearchHistoryList(searchTexts: List<SearchTexts>) {
      val scrollState2 = rememberScrollState()
    if (searchTexts.isEmpty()) {
        EmptyHistoryMessage()
    } else {
        Column(
            modifier = Modifier.fillMaxWidth().verticalScroll(scrollState2),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            searchTexts.forEach { search ->
                SearchContent(
                    text = search.query,
                    timeDate = search.searchedAt.toString()
                )
            }
        }
    }
}

@Composable
private fun EmptyHistoryMessage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.no_history_found),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

