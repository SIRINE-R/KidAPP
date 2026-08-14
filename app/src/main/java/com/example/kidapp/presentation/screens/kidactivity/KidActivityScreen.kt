package com.example.kidapp.presentation.screens.kidactivity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.kidapp.R
import com.example.kidapp.presentation.screens.components.ActivityContainer
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

    val scrollState1 = rememberScrollState()
    val scrollState2 = rememberScrollState()


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
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    ExposedDropdownMenuBox(
                        expanded = isExpanded,
                        onExpandedChange = { viewModel.onExpandClick() },
                        modifier = Modifier.clip(RoundedCornerShape(30.dp))
                    ) {
                        TextField(
                            modifier = Modifier.menuAnchor(),
                            readOnly = true,
                            value = selectedName,
                            onValueChange = {},
                            label = { Text(text = "Click to choose kid's name") }
                        )
                        ExposedDropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = { viewModel.onExpandClick() }
                        ) {
                            children.forEach {
                                DropdownMenuItem(
                                    text = { Text(it.name) },
                                    onClick = {
                                        viewModel.onSelectedNameChange(it.name)
                                        viewModel.onSelectedChildIdChange(it.id)
                                        viewModel.onExpandClick()
                                        viewModel.getWatchedVideosAndSearchTexts(it.id, it.name)
                                    }
                                )
                            }
                        }
                    }
                }
            }

            item {
                ActivityContainer(
                    title = stringResource(R.string.watch_history),
                    content = {
                        if (videos.isEmpty()) {
                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                Text(text = "No history found", color = MaterialTheme.colorScheme.primary)
                            }
                        } else {
                            Column(
                                modifier = Modifier.verticalScroll(state = scrollState1)
                            ) {
                                videos.forEach {
                                    KidWatchedVideoCard(
                                        title = it.title,
                                        description = it.description,
                                        thumbnail = it.thumbnail_url,
                                        onClick = {
                                            navController.navigate(
                                                Routes.PLAYERSCREEN.name + "/${it.video_id}/watched/${selectedChildId}"
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                )
            }
            item {
                ActivityContainer(
                    title = stringResource(R.string.search_history),
                    content = {
                        if (searchTexts.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No history found",
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                            else {
                                Column(
                                    modifier = Modifier.verticalScroll(state = scrollState2)
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
                )
            }
        }
    }
}
