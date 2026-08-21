package com.example.kidapp.presentation.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.kidapp.R
import com.example.kidapp.domain.model.Child
import com.example.kidapp.presentation.screens.components.KidButton
import com.example.kidapp.presentation.screens.components.KidTextField
import com.example.kidapp.presentation.screens.components.ProfileImage
import com.example.kidapp.presentation.screens.components.SectionEditing
import com.example.kidapp.util.Routes
import com.example.kidapp.presentation.screens.addprofile.icons
import com.example.kidapp.presentation.screens.components.AppNavigationSuite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    email: String?,
    id: Int?,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val formState by settingsViewModel.formState.collectAsState()
    val children by settingsViewModel.child.collectAsState()

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isCompact = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT

    LaunchedEffect(id) {
        settingsViewModel.getChildren(id)
    }
    LaunchedEffect(email) {
        settingsViewModel.onEmailUpdate(email)
        settingsViewModel.deleteParentEmail(email)
    }

    AppNavigationSuite(
        isHomeSelected = false,
        isSettingsSelected = true,
        isActivitySelected = false,
        isAddSelected = false,
        onHomeClick = { navController.navigate(Routes.PROFILES.name + "/$email/$id") },
        onSettingsClick = { },
        onActivityClick = { navController.navigate(Routes.ACTIVITY.name + "/$email/$id") },
        onAddClick = { navController.navigate(Routes.ADDING.name + "/$email/$id") },
        content = {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(if (isCompact) 16.dp else 24.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 600.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
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
                                    modifier = Modifier.fillMaxWidth()
                                )
                                KidTextField(
                                    value = formState.passwordUpdate,
                                    onValueChange = { settingsViewModel.onPasswordUpdate(it) },
                                    label = stringResource(R.string.password),
                                    visualTransformation = PasswordVisualTransformation(),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                KidButton(
                                    onClick = { settingsViewModel.onUpdateParentClick(id = id) },
                                    text = stringResource(R.string.update),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            } else if (formState.isParentDeleting) {
                                formState.deleteEmail?.let { deleteEmailValue ->
                                    KidTextField(
                                        value = deleteEmailValue,
                                        onValueChange = { settingsViewModel.deleteParentEmail(it) },
                                        label = stringResource(R.string.email),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    KidButton(
                                        onClick = { settingsViewModel.onDeleteParentClick(deleteEmailValue) },
                                        text = stringResource(R.string.delete),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            } else {
                                Text(
                                    text = stringResource(R.string.choose_update_or_delete),
                                    style = MaterialTheme.typography.titleMedium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(16.dp)
                                )
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
                                if (isCompact) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(16.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        ChildDropdownSelector(
                                            formState = formState,
                                            children = children,
                                            settingsViewModel = settingsViewModel,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        ChildIconPicker(
                                            formState = formState,
                                            settingsViewModel = settingsViewModel
                                        )
                                    }
                                } else {
                                    if (formState.isChoosingIcon) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(16.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            ChildDropdownSelector(
                                                formState = formState,
                                                children = children,
                                                settingsViewModel = settingsViewModel,
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                            ChildIconPicker(
                                                formState = formState,
                                                settingsViewModel = settingsViewModel,
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        }
                                    } else {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            ChildDropdownSelector(
                                                formState = formState,
                                                children = children,
                                                settingsViewModel = settingsViewModel,
                                                modifier = Modifier.weight(1f)
                                            )
                                            ChildIconPicker(
                                                formState = formState,
                                                settingsViewModel = settingsViewModel
                                            )
                                        }
                                    }
                                }
                                KidTextField(
                                    value = formState.childName,
                                    onValueChange = { settingsViewModel.onChildNameChange(it) },
                                    label = stringResource(R.string.kid_s_name),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                KidTextField(
                                    value = formState.childAge,
                                    onValueChange = { settingsViewModel.onChildAgeChange(it) },
                                    label = stringResource(R.string.kid_s_age),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                KidButton(
                                    onClick = { settingsViewModel.onUpdateChildClick() },
                                    text = stringResource(R.string.update),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            } else if (formState.isKidDeleting) {
                                KidTextField(
                                    value = formState.childDeleteName,
                                    onValueChange = { settingsViewModel.onChildDeleteNameChange(it) },
                                    label = stringResource(R.string.kid_s_name),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                KidButton(
                                    onClick = { settingsViewModel.onDeleteChildClick(formState.childIdUpdate) },
                                    text = stringResource(R.string.delete),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            } else {
                                Text(
                                    text = stringResource(R.string.choose_update_or_delete),
                                    style = MaterialTheme.typography.titleMedium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChildDropdownSelector(
    formState: SettingsViewModel.SettingsFormState,
    children: List<Child>,
    settingsViewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) {
    ExposedDropdownMenuBox(
        expanded = formState.isExpanded,
        onExpandedChange = { settingsViewModel.onExpandClick() },
        modifier = modifier
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true)
                .fillMaxWidth(),
            readOnly = true,
            value = formState.selectedName,
            onValueChange = {},
            label = { Text(text = stringResource(R.string.click_to_choose_kid_s_name)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = formState.isExpanded) },
            shape = RoundedCornerShape(30.dp),
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = formState.isExpanded,
            onDismissRequest = { settingsViewModel.onExpandClick() }
        ) {
            if (children.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("No children found") },
                    onClick = { settingsViewModel.onExpandClick() }
                )
            } else {
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
    }
}

@Composable
private fun ChildIconPicker(
    formState: SettingsViewModel.SettingsFormState,
    settingsViewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (!formState.isChoosingIcon) {
            ProfileImage(
                profileImage = if (formState.selectedIcon != 0) formState.selectedIcon else R.drawable.panda,
                onClick = { settingsViewModel.updateIsChoosingIcon(true) }
            )
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                items(icons) { icon ->
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
}
