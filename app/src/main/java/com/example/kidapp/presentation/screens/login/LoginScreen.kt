package com.example.myapplication.kid_activity.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.kidapp.R
import com.example.kidapp.presentation.screens.components.KidButton
import com.example.kidapp.presentation.screens.components.KidTextField
import com.example.kidapp.presentation.screens.login.LoginUIState
import com.example.kidapp.presentation.screens.login.LoginViewModel
import com.example.kidapp.util.Routes

@Composable
fun LoginScreen(
    navigation: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val loginUiState by loginViewModel.loginUIState.collectAsState()

    LaunchedEffect(loginUiState) {
        if (loginUiState is LoginUIState.Success) {
            navigation.navigate(
                Routes.PROFILES.name
                        +"/${loginViewModel.email.value}"
                        +"/${loginViewModel.id.value}"
            )
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.login),
            style = MaterialTheme.typography.headlineLarge
        )
        if (loginUiState is LoginUIState.Error) {
            Text(
                text = (loginUiState as LoginUIState.Error).message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
        KidTextField(
            value = loginViewModel.email.value,
            onValueChange = { loginViewModel.onEmailChange(it) },
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
            placeholder = stringResource(R.string.enter_your_email)
        )
        KidTextField(
            value = loginViewModel.password.value,
            onValueChange = { loginViewModel.onPasswordChange(it) },
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
            visualTransformation = PasswordVisualTransformation()
        )
        KidButton(
            text = stringResource(R.string.login),
            onClick = {
                loginViewModel.onLoginClick()
            },
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.don_t_have_an_account),
                style = MaterialTheme.typography.bodyMedium
            )
            TextButton(onClick = {
                navigation.navigate(Routes.SIGNUP.name)
            }) {
                Text(
                    text = stringResource(R.string.sign_up),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
    }

}

