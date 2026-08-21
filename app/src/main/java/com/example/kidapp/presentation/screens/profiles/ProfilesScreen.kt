package com.example.kidapp.presentation.screens.profiles

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.kidapp.R
import com.example.kidapp.domain.model.ChildProfile
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

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isCompact = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT

    LaunchedEffect(key1 = id) {
        profilesViewModel.getProfileData(id)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(if (isCompact) 16.dp else 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = stringResource(R.string.who_is_watching),
                style = if (isCompact) {
                    MaterialTheme.typography.headlineMedium
                } else {
                    MaterialTheme.typography.headlineLarge
                },
                textAlign = TextAlign.Center
            )

            ProfileImage(
                profileImage = R.drawable.mother,
                name = parent?.name ?: stringResource(R.string.parent),
                onClick = {
                    navigation.navigate(Routes.VERIFYING.name + "/$email/$id")
                }
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
            )

            if (isCompact) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(
                        items = children,
                        key = { child -> child.id!! }
                    ) { child ->
                        ChildProfileItem(
                            child = child,
                            profilesViewModel = profilesViewModel,
                            navigation = navigation
                        )
                    }
                }
            } else {
                OptIn(ExperimentalLayoutApi::class)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    children.forEach { child ->
                        ChildProfileItem(
                            child = child,
                            profilesViewModel = profilesViewModel,
                            navigation = navigation
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChildProfileItem(
    child: ChildProfile,
    profilesViewModel: ProfilesViewModel,
    navigation: NavHostController
) {
    ProfileImage(
        profileImage = profilesViewModel.getDrawableRes(child.iconProfile),
        name = child.name,
        onClick = {
            try {
                navigation.navigate(
                    Routes.KIDHOME.name + "/${child.iconProfile}/${child.id}"
                )
            } catch (e: Exception) {
                Log.e("NavigationCrash", "Failed to navigate to KidHomeScreen", e)
            }
        }
    )
}