package com.example.kidapp.presentation.screens.profiles

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.kidapp.R
import com.example.kidapp.presentation.screens.components.ProfileImage
import com.example.kidapp.util.Routes

@Composable
fun ProfilesScreen(
    navigation: NavHostController,
    email: String?,
    id: Int?,
    profilesViewModel: ProfilesViewModel = hiltViewModel()
) {
    val children by profilesViewModel.children.collectAsState()
    val parent by profilesViewModel.parent.collectAsState()

    LaunchedEffect(key1 = id) {
        profilesViewModel.getProfileData(id)
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        ProfileImage(
            profileImage = R.drawable.mother,
            name = parent?.name,
            onClick = {
                navigation.navigate(Routes.VERIFYING.name+"/$email"+"/$id")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.height(150.dp)
        ) {
            items(children) { child ->
                ProfileImage(
                    profileImage = profilesViewModel.getDrawableRes(child.iconProfile),
                    name = child.name,
                    onClick = {
                        try {
                            navigation.navigate(
                                Routes.KIDHOME.name+
                                        "/${child.iconProfile}" + "/${child.id}")
                        } catch (e: Exception) {
                            Log.e("NavigationCrash", "Failed to navigate to KidHomeScreen", e)
                        }
                    }
                )
            }
        }
    }
}