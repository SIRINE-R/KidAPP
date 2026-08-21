package com.example.kidapp.presentation.screens.addprofile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.kidapp.R
import com.example.kidapp.presentation.screens.components.AppNavigationSuite
import com.example.kidapp.presentation.screens.components.KidButton
import com.example.kidapp.presentation.screens.components.KidTextField
import com.example.kidapp.presentation.screens.components.ProfileImage
import com.example.kidapp.util.Routes


val icons = listOf(
    R.drawable.rabbit,
    R.drawable.bob,
    R.drawable.bird,
    R.drawable.dorimen,
    R.drawable.jerry,
    R.drawable.mikymouse,
    R.drawable.panda,
)
@Composable
fun AddProfileScreen(
    navController: NavHostController,
    childViewModel: ChildProfileViewModel = hiltViewModel(),
    email: String?,
    id: Int?
) {
    val uiState by childViewModel.uiState.collectAsStateWithLifecycle()
    val selectedIcon by childViewModel.selectedIcon.collectAsState()
    val isChoosingIcon by childViewModel.isChoosingIcon.collectAsState()
    val name by childViewModel.name.collectAsState()
    val birthDate by childViewModel.birthDate.collectAsState()

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isCompact = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT

    LaunchedEffect(uiState) {
        if (uiState is ChildProfileUIState.Success) {
            navController.navigate(Routes.PROFILES.name + "/$email/$id") {
                popUpTo(Routes.ADDING.name + "/$email/$id") { inclusive = true }
            }
        }
    }

    AppNavigationSuite(
        isHomeSelected = false,
        isSettingsSelected = false,
        isActivitySelected = false,
        isAddSelected = true,
        onHomeClick = { navController.navigate(Routes.PROFILES.name + "/$email/$id") },
        onSettingsClick = { navController.navigate(Routes.SETTINGS.name + "/$email/$id") },
        onActivityClick = { navController.navigate(Routes.ACTIVITY.name + "/$email/$id") },
        onAddClick = { },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(if (isCompact) 16.dp else 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 500.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    if (isCompact) {
                        Text(
                            text = stringResource(R.string.choose_your_kids_s_icon_profile),
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                        ProfileIconSelector(
                            isChoosingIcon = isChoosingIcon,
                            selectedIcon = selectedIcon,
                            onIconClick = { childViewModel.updateIsChoosingIcon(true) },
                            onIconSelected = { icon ->
                                childViewModel.onSelectedIconChange(icon)
                                childViewModel.updateIsChoosingIcon(false)
                            }
                        )
                    } else {
                        if (isChoosingIcon) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = stringResource(R.string.choose_your_kids_s_icon_profile),
                                    style = MaterialTheme.typography.titleLarge,
                                    textAlign = TextAlign.Center
                                )
                                ProfileIconSelector(
                                    isChoosingIcon = isChoosingIcon,
                                    selectedIcon = selectedIcon,
                                    onIconClick = { childViewModel.updateIsChoosingIcon(true) },
                                    onIconSelected = { icon ->
                                        childViewModel.onSelectedIconChange(icon)
                                        childViewModel.updateIsChoosingIcon(false)
                                    }
                                )
                            }
                        } else {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(24.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = stringResource(R.string.choose_your_kids_s_icon_profile),
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.weight(1f)
                                )
                                ProfileIconSelector(
                                    isChoosingIcon = isChoosingIcon,
                                    selectedIcon = selectedIcon,
                                    onIconClick = { childViewModel.updateIsChoosingIcon(true) },
                                    onIconSelected = { icon ->
                                        childViewModel.onSelectedIconChange(icon)
                                        childViewModel.updateIsChoosingIcon(false)
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    KidTextField(
                        value = name,
                        onValueChange = { childViewModel.onNameChange(it) },
                        label = stringResource(R.string.kid_s_name),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Face,
                                contentDescription = null
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        placeholder = stringResource(R.string.enter_your_kid_s_name),
                        modifier = Modifier.fillMaxWidth()
                    )

                    KidTextField(
                        value = birthDate,
                        onValueChange = { childViewModel.onBirthDateChange(it) },
                        label = stringResource(R.string.kid_s_age),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Face,
                                contentDescription = null
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        placeholder = stringResource(R.string.enter_your_kid_s_age),
                        modifier = Modifier.fillMaxWidth()
                    )

                    KidButton(
                        onClick = { childViewModel.onCreateProfileClick(id) },
                        text = stringResource(R.string.add),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                }
            }
        }
    )
}

@Composable
private fun ProfileIconSelector(
    isChoosingIcon: Boolean,
    selectedIcon: Int,
    onIconClick: () -> Unit,
    onIconSelected: (Int) -> Unit
) {
    if (!isChoosingIcon) {
        ProfileImage(
            profileImage = if (selectedIcon != 0) selectedIcon else R.drawable.panda,
            onClick = onIconClick
        )
    } else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(icons) { icon ->
                ProfileImage(
                    profileImage = icon,
                    onClick = { onIconSelected(icon) }
                )
            }
        }
    }
}


