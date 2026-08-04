package com.example.myapplication.kid_activity.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.kidapp.presentation.screens.components.SearchBar
import com.example.kidapp.presentation.screens.components.VideoCard
import com.example.kidapp.presentation.screens.search.SearchViewModel
import com.example.kidapp.presentation.screens.search.SerachScreenUIState
import androidx.compose.material3.CircularProgressIndicator
import com.example.kidapp.util.Routes

@Composable
fun SearchScreen(
    navController: NavHostController,
    iconProfile: String?,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val queryText by searchViewModel.queryText.collectAsState()
    val uiState by searchViewModel.uiState.collectAsState()
    val videos by searchViewModel.video.collectAsState()


    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            value = queryText,
            onValueChange = { searchViewModel.onQueryChanged(it) },
            onSearch = {
                keyboardController?.hide()
                searchViewModel.getVideos()
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            painter = searchViewModel.getDrawableRes(iconProfile),
        )
         when(uiState) {
             is SerachScreenUIState.Error -> {
                 val errorMessage = (uiState as SerachScreenUIState.Error).message
                 Text(
                     text = errorMessage,
                     color = Color.Red,
                     style = typography.bodySmall,
                     modifier = Modifier.align(Alignment.CenterHorizontally)
                 )
             }
             is SerachScreenUIState.Loading -> {
                 CircularProgressIndicator(
                     modifier = Modifier.align(Alignment.CenterHorizontally)
                 )
             }
             is SerachScreenUIState.Success -> {
                 LazyVerticalGrid(
                     columns = GridCells.Fixed(2),
                     modifier = Modifier.padding(8.dp)
                 ) {
                     items(videos) { video ->
                         VideoCard(
                             title = video.title,
                             description = video.description,
                             thumbnail = video.thumbnail_url,
                             onClick = {
                                 navController.navigate(Routes.PLAYERSCREEN.name + "/${video.video_id}/${queryText}")
                             }
                         )
                     }
                 }

             }

             else -> {

             }
         }

    }
}

