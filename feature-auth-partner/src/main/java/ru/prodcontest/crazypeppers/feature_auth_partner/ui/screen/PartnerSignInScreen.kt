package ru.prodcontest.crazypeppers.feature_auth_partner.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.prodcontest.crazypeppers.feature_auth_partner.R
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import ru.prodcontest.crazypeppers.common.utils.PhoneNumberVisualTransformation
import ru.prodcontest.crazypeppers.feature_auth_partner.domain.state.AuthState
import ru.prodcontest.crazypeppers.feature_auth_partner.ui.viewmodel.PartnerSignInViewModel

@Composable
fun PartnerSignInScreen(
    navigateOnSignInSuccess: () -> Unit,
    viewModel: PartnerSignInViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val phoneNumber by viewModel.phoneNumber.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        if (state is AuthState.SignInSuccess) {
            viewModel.onSignInSuccess()
            navigateOnSignInSuccess()
        }
    }

    PartnerSignInScreenContent(
        state = state,
        phoneNumber = phoneNumber,
        password = password,
        onPhoneNumberChange = viewModel::onPhoneNumberChanged,
        onPasswordChange = viewModel::onPasswordChanged,
        onSignInClick = viewModel::onSignInClicked
    )
}

@Composable
private fun PartnerSignInScreenContent(
    state: AuthState,
    phoneNumber: String,
    password: String,
    onPhoneNumberChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = onPhoneNumberChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = state !is AuthState.Loading,
                visualTransformation = if (phoneNumber.length <= 11) PhoneNumberVisualTransformation else VisualTransformation.None,
                label = {
                    Text(
                        text = stringResource(id = R.string.phone_number)
                    )
                },
                isError = state is AuthState.Error,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )

            OutlinedTextField(
                value = password,
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = onPasswordChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = state !is AuthState.Loading,
                label = {
                    Text(
                        text = stringResource(id = R.string.password)
                    )
                },
                supportingText = {
                    if (state is AuthState.Error) {
                        Text(
                            text = state.message,
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onSignInClick() }
                ),
                singleLine = true
            )

            Button(
                onClick = onSignInClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .safeDrawingPadding(),
                enabled = state !is AuthState.Loading
            ) {
                Text(
                    text = stringResource(id = R.string.sign_in)
                )
            }
        }

        if (state is AuthState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
