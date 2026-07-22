package com.example.myapplication.kid_activity.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.kidapp.R
import com.example.kidapp.presentation.screens.addprofile.ChildProfileUIState
import com.example.kidapp.presentation.screens.addprofile.ChildProfileViewModel
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

    LaunchedEffect(uiState) {
        if (uiState is ChildProfileUIState.Success){
            navController.navigate(Routes.PROFILES.name + "/$email/$id") {
                popUpTo(Routes.ADDING.name + "/$email/$id") { inclusive = true }
            }
        }
    }
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = null
                        )
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Routes.SETTINGS.name + "/$email/$id") },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = null
                        )
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Routes.ACTIVITY.name + "/$email/$id") },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Face,
                            contentDescription = null
                        )
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Routes.PROFILES.name + "/$email/$id") },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically ,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.choose_your_kids_s_icon_profile),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(16.dp) ,

                )
                Spacer(modifier = Modifier.padding(horizontal = 40.dp))
                if(!isChoosingIcon) {
                    ProfileImage(
                        profileImage = if (selectedIcon != 0) selectedIcon else R.drawable.panda,
                        onClick = {
                            childViewModel.updateIsChoosingIcon(true)
                        }
                    )
                } else {
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally ,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.size(150.dp)
                    ) {
                        items(icons) {icon ->
                            ProfileImage(
                                profileImage = icon,
                                onClick = {
                                    childViewModel.onSelectedIconChange(icon)
                                    childViewModel.updateIsChoosingIcon(false)
                                }
                            )
                    }
                    }
                }
            }
            Column {
                KidTextField(
                    value = childViewModel.name.collectAsState().value,
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
                    placeholder = stringResource(R.string.enter_your_kid_s_name)

                )
                KidTextField(
                    value = childViewModel.birthDate.collectAsState().value,
                    onValueChange = { childViewModel.onBirthDateChange(it) },
                    label = stringResource(R.string.kid_s_age),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Face,
                            contentDescription = null
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    placeholder = stringResource(R.string.enter_your_kid_s_age)

                )
                KidButton(
                    onClick = { childViewModel.onCreateProfileClick(id) },
                    text = stringResource(R.string.add),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

