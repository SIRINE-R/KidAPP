package com.example.kidapp.presentation.screens.kidscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.kidapp.presentation.screens.components.SearchBar
import com.example.kidapp.util.Routes

@Composable
fun KidHomeScreen(
    navController: NavHostController,
    iconProfile: String?,
    childId: Int?,
    kidHomeViewModel: KidHomeScreenViewModel = hiltViewModel()
) {


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
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(8.dp)
            ) {

            }
        }
    }

