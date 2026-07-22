package com.example.kidapp.presentation.screens.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.kidapp.R
import com.example.kidapp.presentation.screens.components.KidButton
import com.example.kidapp.presentation.screens.components.KidTextField
import com.example.kidapp.util.Routes
import java.nio.file.WatchEvent

@Composable
fun SignUpScreen(
    navigation: NavHostController,
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {
    val signUpStatus by signUpViewModel.registerUiState.collectAsStateWithLifecycle()
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
    // Handle navigation when successful
    LaunchedEffect(signUpStatus) {
        if (signUpStatus is SignUpUiState.Success) {
            val parent = (signUpStatus as SignUpUiState.Success).parent
            navigation.navigate(Routes.PROFILES.name + "/${parent.email}/${parent.id}") {
                popUpTo(Routes.SIGNUP.name) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.sign_up),
            style = MaterialTheme.typography.headlineLarge
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        KidTextField(
            value = signUpViewModel.name.value,
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
            isError = signUpViewModel.nameError.value != null
        )
        signUpViewModel.nameError.value?.let { errorMessage ->
            Text(text = errorMessage, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        KidTextField(
            value = signUpViewModel.email.value,
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
            isError = signUpViewModel.passwordError.value != null
        )
        signUpViewModel.nameError.value?.let{
            errorMessage ->
            Text(text = errorMessage, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }
        KidTextField(
            value = signUpViewModel.password.value,
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
            isError = signUpViewModel.emailError.value != null,
            visualTransformation = PasswordVisualTransformation()
        )
        signUpViewModel.passwordError.value?.let{
            errorMessage ->
            Text(text = errorMessage, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(16.dp))


        // Show error message if exists
        if (signUpStatus is SignUpUiState.Error) {
            Text(
                text = (signUpStatus as SignUpUiState.Error).message,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Show loading or button
        if (signUpStatus is SignUpUiState.Loading) {
            CircularProgressIndicator()
        } else {
            KidButton(
                text = stringResource(R.string.sign_up),
                onClick = {
                    signUpViewModel.onSignUpClick(values = values)
                }
            )
        }
    }

}
