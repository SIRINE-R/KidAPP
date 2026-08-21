package com.example.kidapp.presentation.screens.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.kidapp.util.Routes
@Composable
fun SignUpScreen(
    navigation: NavHostController,
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {
    val signUpStatus by signUpViewModel.registerUiState.collectAsStateWithLifecycle()
    val name by signUpViewModel.name.collectAsStateWithLifecycle()
    val email by signUpViewModel.email.collectAsStateWithLifecycle()
    val password by signUpViewModel.password.collectAsStateWithLifecycle()
    val nameError by signUpViewModel.nameError.collectAsStateWithLifecycle()
    val emailError by signUpViewModel.emailError.collectAsStateWithLifecycle()
    val passwordError by signUpViewModel.passwordError.collectAsStateWithLifecycle()

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isCompact = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT

    val values = listOf(
        stringResource(R.string.name_is_required),
        stringResource(R.string.email_is_required),
        stringResource(R.string.invalid_email_format),
        stringResource(R.string.password_is_required),
        stringResource(R.string.password_must_be_at_least_8_characters_long),
        stringResource(R.string.something_went_wrong_please_check_your_inputs),
        stringResource(R.string.cannot_connect_to_server_check_your_network),
        stringResource(R.string.an_unexpected_error_occurred)
    )

    LaunchedEffect(signUpStatus) {
        if (signUpStatus is SignUpUiState.Success) {
            val parent = (signUpStatus as SignUpUiState.Success).parent
            navigation.navigate(Routes.PROFILES.name + "/${parent.email}/${parent.id}") {
                popUpTo(Routes.SIGNUP.name) { inclusive = true }
            }
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.sign_up),
                style = if (isCompact) {
                    MaterialTheme.typography.headlineLarge
                } else {
                    MaterialTheme.typography.displaySmall
                },
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            KidTextField(
                value = name,
                onValueChange = { signUpViewModel.onNameChange(it) },
                label = stringResource(R.string.username),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = stringResource(R.string.user_icon)
                    )
                },
                placeholder = stringResource(R.string.enter_your_name),
                isError = nameError != null,
                modifier = Modifier.fillMaxWidth()
            )
            nameError?.let { errorMessage ->
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            KidTextField(
                value = email,
                onValueChange = { signUpViewModel.onEmailChange(it) },
                label = stringResource(R.string.email),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = stringResource(R.string.email_icon)
                    )
                },
                placeholder = stringResource(R.string.enter_your_email),
                isError = emailError != null,
                modifier = Modifier.fillMaxWidth()
            )
            emailError?.let { errorMessage ->
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            KidTextField(
                value = password,
                onValueChange = { signUpViewModel.onPasswordChange(it) },
                label = stringResource(R.string.password),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = stringResource(R.string.password_icon)
                    )
                },
                placeholder = stringResource(R.string.enter_your_password),
                isError = passwordError != null,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            passwordError?.let { errorMessage ->
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            if (signUpStatus is SignUpUiState.Error) {
                Text(
                    text = (signUpStatus as SignUpUiState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (signUpStatus is SignUpUiState.Loading) {
                CircularProgressIndicator()
            } else {
                KidButton(
                    text = stringResource(R.string.sign_up),
                    onClick = { signUpViewModel.onSignUpClick(values = values) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            TextButton(onClick = { navigation.navigate(Routes.LOGIN.name) }) {
                Text(
                    text = stringResource(R.string.have_account),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}