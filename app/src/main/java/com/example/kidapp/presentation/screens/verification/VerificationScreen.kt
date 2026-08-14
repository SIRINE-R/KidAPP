package com.example.myapplication.kid_activity.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.kidapp.R
import com.example.kidapp.presentation.screens.components.KidButton
import com.example.kidapp.presentation.screens.components.KidTextField
import com.example.kidapp.presentation.screens.components.ProfileImage
import com.example.kidapp.presentation.screens.verification.VerifyUIState
import com.example.kidapp.presentation.screens.verification.VerifyViewModel
import com.example.kidapp.util.Routes
import java.util.ArrayList


@Composable
fun VerificationScreen(
    navigation: NavHostController,
    verifyViewModel: VerifyViewModel = hiltViewModel(),
    email: String?,
    id: Int?
) {
    val uiState by verifyViewModel.verifyUiState.collectAsState()


    LaunchedEffect(uiState) {
        if (uiState is VerifyUIState.Success) {
            navigation.navigate(Routes.SETTINGS.name + "/$email/$id")
        }
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        ProfileImage(
            profileImage = R.drawable.mother,
        )
        if (uiState is VerifyUIState.Error) {
            Text(
                text = (uiState as VerifyUIState.Error).message,
                color = androidx.compose.ui.graphics.Color.Red,
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall
            )
        }
        KidTextField(
            value = verifyViewModel.verifyPassword.value,
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
            visualTransformation = PasswordVisualTransformation()
        )
        KidButton(
            onClick = {
                    verifyViewModel.onVerifyClick(email)


            },
            text = stringResource(R.string.verify)
        )
    }

}
