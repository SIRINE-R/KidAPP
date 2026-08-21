package com.example.kidapp.presentation.screens.verification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.kidapp.R
import com.example.kidapp.presentation.screens.components.KidButton
import com.example.kidapp.presentation.screens.components.KidTextField
import com.example.kidapp.presentation.screens.components.ProfileImage
import com.example.kidapp.util.Routes

@Composable
fun VerificationScreen(
    navigation: NavHostController,
    verifyViewModel: VerifyViewModel = hiltViewModel(),
    email: String?,
    id: Int?
) {
    val uiState by verifyViewModel.verifyUiState.collectAsStateWithLifecycle()
    val verifyPassword by verifyViewModel.verifyPassword.collectAsStateWithLifecycle()

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isCompact = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT

    LaunchedEffect(uiState) {
        if (uiState is VerifyUIState.Success) {
            navigation.navigate(Routes.SETTINGS.name + "/$email/$id")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(if (isCompact) 16.dp else 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 420.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProfileImage(
                profileImage = R.drawable.mother,
            )

            if (uiState is VerifyUIState.Error) {
                Text(
                    text = (uiState as VerifyUIState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }

            KidTextField(
                value = verifyPassword,
                onValueChange = { verifyViewModel.onVerifyPasswordChange(it) },
                label = stringResource(R.string.password),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null
                    )
                },
                placeholder = stringResource(R.string.enter_your_password),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            if (uiState is VerifyUIState.Loading) {
                CircularProgressIndicator()
            } else {
                KidButton(
                    onClick = { verifyViewModel.onVerifyClick(email) },
                    text = stringResource(R.string.verify),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}