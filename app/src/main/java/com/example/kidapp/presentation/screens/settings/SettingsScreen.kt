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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kidapp.R
import com.example.kidapp.presentation.screens.components.KidButton
import com.example.kidapp.presentation.screens.components.KidTextField
import com.example.kidapp.presentation.screens.components.ProfileImage
import com.example.kidapp.presentation.screens.components.SectionEditing
import com.example.kidapp.util.Routes
import com.example.myapplication.kid_activity.presentation.screens.icons


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    email: String?, id: Int?,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val formState by settingsViewModel.formState.collectAsState()

    LaunchedEffect(id) {
        settingsViewModel.getChildren(id)
    }
    LaunchedEffect(email) {
        settingsViewModel.onEmailUpdate(email)
    }
    val children by settingsViewModel.child.collectAsState()


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
                isUpdateSelected = formState.isParentUpdating,
                isDeleteSelected = formState.isParentDeleting,
                onUpdateSelected = {
                    settingsViewModel.updateParentUpdating(true)
                    settingsViewModel.updateParentDeleting(false)
                },
                onDeleteSelected = {
                    settingsViewModel.updateParentDeleting(true)
                    settingsViewModel.updateParentUpdating(false)
                },
                content = {
                    if (formState.isParentUpdating) {
                            KidTextField(
                                value = formState.emailUpdate,
                                onValueChange = { settingsViewModel.onEmailUpdate(it) },
                                label = stringResource(R.string.email),
                            )
                        KidTextField(
                            value = formState.passwordUpdate,
                            onValueChange = { settingsViewModel.onPasswordUpdate(it) },
                            label = stringResource(R.string.password),
                            visualTransformation = PasswordVisualTransformation()
                        )
                        KidButton(
                            onClick = {
                                settingsViewModel.onUpdateParentClick(id = id)
                            },
                            text = stringResource(R.string.update),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    } else if (formState.isParentDeleting) {
                        KidTextField(
                            value = formState.deleteEmail,
                            onValueChange = { settingsViewModel.updateEmailName(it) },
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
                isUpdateSelected = formState.isKidUpdating,
                isDeleteSelected = formState.isKidDeleting,
                onUpdateSelected = {
                    settingsViewModel.updateKidUpdating(true)
                    settingsViewModel.updateKidDeleting(false)
                },
                onDeleteSelected = {
                    settingsViewModel.updateKidDeleting(true)
                    settingsViewModel.updateKidUpdating(false)
                },
                content = {
                    if (formState.isKidUpdating) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            ExposedDropdownMenuBox(
                                expanded = formState.isExpanded,
                                onExpandedChange = { settingsViewModel.onExpandClick() },
                                modifier = Modifier.clip(RoundedCornerShape(30.dp)
                                )
                            ) {
                                TextField(
                                    modifier = Modifier
                                        .menuAnchor(),
                                    readOnly = true,
                                    value = formState.selectedName,
                                    onValueChange = {},
                                    label = {Text(text = "Click to choose kid's name")}
                                )
                                ExposedDropdownMenu(
                                    expanded = formState.isExpanded,
                                    onDismissRequest = { settingsViewModel.onExpandClick() }
                                ) {
                                    children.forEach { child ->
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
                            if(!formState.isChoosingIcon) {
                                ProfileImage(
                                    profileImage = if (formState.selectedIcon != 0) formState.selectedIcon else R.drawable.panda,
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
                            value = formState.childName,
                            onValueChange = { settingsViewModel.onChildNameChange(it) },
                            label = stringResource(R.string.kid_s_name),
                        )
                        KidTextField(
                            value = formState.childAge,
                            onValueChange = { settingsViewModel.onChildAgeChange(it) },
                            label = stringResource(R.string.kid_s_age),
                        )
                        KidButton(
                            onClick = {settingsViewModel.onUpdateChildClick() },
                            text = stringResource(R.string.update),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    } else if (formState.isKidDeleting) {
                        KidTextField(
                            value = formState.childDeleteName,
                            onValueChange = { settingsViewModel.onChildDeleteNameChange(it) },
                            label = stringResource(R.string.kid_s_name),
                        )
                        KidButton(
                            onClick = { settingsViewModel.onDeleteChildClick(formState.childIdUpdate) },
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

