package com.example.kidapp.presentation.screens.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kidapp.R
import com.example.kidapp.presentation.screens.components.KidButton
import com.example.kidapp.presentation.screens.components.KidTextField
import com.example.kidapp.presentation.screens.components.ProfileImage
import com.example.kidapp.presentation.screens.components.SectionEditing
import com.example.kidapp.presentation.screens.viewModels.SettingsViewModel2
import com.example.kidapp.util.Routes
import com.example.myapplication.kid_activity.presentation.screens.icons


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    email: String?, id: Int?,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val viewModel: SettingsViewModel2 = viewModel()
    val uiState = viewModel.uiState.collectAsState()
    val childUIState by settingsViewModel.childSettingsUIState.collectAsState()

    LaunchedEffect(id) {
        settingsViewModel.getChildren(id)
    }
    val children by settingsViewModel.child.collectAsState()
    val selectedName by settingsViewModel.selectedName.collectAsState()
    val selectedIcon by settingsViewModel.selectedIcon.collectAsState()
    val isChoosingIcon by settingsViewModel.isChoosingIcon.collectAsState()
    var childID = 0
   
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
                    selected = true,
                    onClick = {  },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null
                        )
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Routes.ACTIVITY.name + "/$email/$id") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SectionEditing(
                title = stringResource(R.string.parent_s_settings),
                isUpdateSelected = uiState.value.isParentUpdating,
                isDeleteSelected = uiState.value.isParentDeleting,
                onUpdateSelected = {
                    viewModel.updateParentUpdating(true)
                    viewModel.updateParentDeleting(false)
                },
                onDeleteSelected = {
                    viewModel.updateParentDeleting(true)
                    viewModel.updateParentUpdating(false)
                },
                content = {
                    if (uiState.value.isParentUpdating) {
                        KidTextField(
                            value = uiState.value.email,
                            onValueChange = { viewModel.updateEmail(it) },
                            label = stringResource(R.string.email),
                        )
                        KidTextField(
                            value = uiState.value.password,
                            onValueChange = { viewModel.updatePassword(it) },
                            label = stringResource(R.string.password),
                        )
                        KidButton(
                            onClick = { /*TODO*/ },
                            text = stringResource(R.string.update),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    } else if (uiState.value.isParentDeleting) {
                        KidTextField(
                            value = uiState.value.deleteEmail,
                            onValueChange = { viewModel.updateEmailName(it) },
                            label = stringResource(R.string.email),
                        )
                        KidButton(
                            onClick = { /*TODO*/ },
                            text = stringResource(R.string.delete),
                            modifier = Modifier.align(Alignment.CenterHorizontally)

                        )
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(R.string.choose_update_or_delete),
                                style = MaterialTheme.typography.titleLarge
                            )

                        }

                    }
                }
            )
            SectionEditing(
                title = stringResource(R.string.kid_s_settings),
                isUpdateSelected = uiState.value.isKidUpdating,
                isDeleteSelected = uiState.value.isKidDeleting,
                onUpdateSelected = {
                    viewModel.updateKidUpdating(true)
                    viewModel.updateKidDeleting(false)
                },
                onDeleteSelected = {
                    viewModel.updateKidDeleting(true)
                    viewModel.updateKidUpdating(false)
                },
                content = {
                    if (uiState.value.isKidUpdating) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            ExposedDropdownMenuBox(
                                expanded = settingsViewModel.isExpandedState.value,
                                onExpandedChange = { settingsViewModel.onExpandClick() },
                                modifier = Modifier.clip(RoundedCornerShape(30.dp)
                                )
                            ) {
                                TextField(
                                    modifier = Modifier
                                        .menuAnchor(),
                                    readOnly = true,
                                    value = selectedName,
                                    onValueChange = {},
                                    label = {Text(text = "Click to choose kid's name")}
                                )
                                ExposedDropdownMenu(
                                    expanded = settingsViewModel.isExpandedState.value,
                                    onDismissRequest = { settingsViewModel.onExpandClick() }
                                ) {
                                    children.forEach { child ->
                                        childID = child.id
                                        DropdownMenuItem(
                                            text = { Text(child.name) },
                                            onClick = {
                                                settingsViewModel.onChildIdChange(child.id)
                                                settingsViewModel.onSelectedNameChange(child.name)
                                                settingsViewModel.onChildNameChange(child.name)
                                                settingsViewModel.onChildAgeChange(child.birthDate)
                                                settingsViewModel.onSelectedIconChange(
                                                    settingsViewModel.getDrawableRes(child.iconProfile)
                                                )
                                                settingsViewModel.onExpandClick()
                                            }
                                        )
                                    }
                                }
                            } 
                            Spacer(modifier = Modifier.weight(1f))
                            if(!isChoosingIcon) {
                                ProfileImage(
                                    profileImage = if (selectedIcon != 0) selectedIcon else R.drawable.panda,
                                    onClick = {
                                        settingsViewModel.updateIsChoosingIcon(true)
                                    },
                                    modifier = Modifier.size(
                                        height = 100.dp, width = 90.dp)
                                        .padding(top = 20.dp)
                                )
                            } else {
                                LazyColumn(
                                    horizontalAlignment = Alignment.CenterHorizontally ,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.size(
                                        height = 100.dp, width = 90.dp)
                                        .padding(top = 20.dp)
                                ) {
                                    items(icons) {icon ->
                                        ProfileImage(
                                            profileImage = icon,
                                            onClick = {
                                                settingsViewModel.onSelectedIconChange(icon)
                                                settingsViewModel.updateIsChoosingIcon(false)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                        KidTextField(
                            value = settingsViewModel.childName.value,
                            onValueChange = { settingsViewModel.onChildNameChange(it) },
                            label = stringResource(R.string.kid_s_name),
                        )
                        KidTextField(
                            value = settingsViewModel.childAge.value,
                            onValueChange = { settingsViewModel.onChildAgeChange(it) },
                            label = stringResource(R.string.kid_s_age),
                        )
                        KidButton(
                            onClick = {settingsViewModel.onUpdateClick() },
                            text = stringResource(R.string.update),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    } else if (uiState.value.isKidDeleting) {
                        KidTextField(
                            value = settingsViewModel.childDeleteName.value,
                            onValueChange = { settingsViewModel.onChildDeleteNameChange(it) },
                            label = stringResource(R.string.kid_s_name),
                        )
                        KidButton(
                            onClick = { settingsViewModel.onDeleteClick(childID) },
                            text = stringResource(R.string.delete),
                            modifier = Modifier.align(Alignment.CenterHorizontally)

                        )
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(R.string.choose_update_or_delete),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                }
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun Preview() {
        SettingsScreen(
            navController = rememberNavController(),
            email = null,
            id = null
        )

}
