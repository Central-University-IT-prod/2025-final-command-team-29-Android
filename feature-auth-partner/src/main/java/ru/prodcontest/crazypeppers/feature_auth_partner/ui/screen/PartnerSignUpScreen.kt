package ru.prodcontest.crazypeppers.feature_auth_partner.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import ru.prodcontest.crazypeppers.common.domain.Images
import ru.prodcontest.crazypeppers.common.utils.PhoneNumberVisualTransformation
import ru.prodcontest.crazypeppers.feature_auth_partner.R
import ru.prodcontest.crazypeppers.feature_auth_partner.domain.state.AuthState
import ru.prodcontest.crazypeppers.feature_auth_partner.ui.elements.ImageOrPlaceholder
import ru.prodcontest.crazypeppers.feature_auth_partner.ui.viewmodel.PartnerSignUpViewModel

@Composable
fun PartnerSignUpScreen(
    navigateOnSignUpSuccess: () -> Unit,
    viewModel: PartnerSignUpViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val title by viewModel.title.collectAsStateWithLifecycle()
    val description by viewModel.description.collectAsStateWithLifecycle()
    val phoneNumber by viewModel.phoneNumber.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        if (state is AuthState.SignUpSuccess) {
            viewModel.onSignUpSuccess()
            navigateOnSignUpSuccess()
        }
    }

    PartnerSignUpScreenContent(
        state = state,
        title = title,
        description = description,
        phoneNumber = phoneNumber,
        password = password,
        onTitleChange = viewModel::onTitleChanged,
        onDescriptionChange = viewModel::onDescriptionChanged,
        onPhoneNumberChange = viewModel::onPhoneNumberChanged,
        onPasswordChange = viewModel::onPasswordChanged,
        onSignUpClick = viewModel::onSignUpClicked,
        imageId = viewModel.imageId
    )
}

@Composable
private fun PartnerSignUpScreenContent(
    imageId: Int?,
    state: AuthState,
    title: String,
    description: String,
    phoneNumber: String,
    password: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit
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
            val image = Images.images[imageId]
            ImageOrPlaceholder(
                painter = if (image == null) null else painterResource(image),
                modifier = Modifier.size(
                    LocalConfiguration.current.screenWidthDp.dp / 4
                )
            )

            OutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = state !is AuthState.Loading,
                label = {
                    Text(
                        text = stringResource(id = R.string.title)
                    )
                },
                isError = state is AuthState.Error,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )

            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = state !is AuthState.Loading,
                label = {
                    Text(
                        text = stringResource(id = R.string.description)
                    )
                },
                isError = state is AuthState.Error,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )

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
                    onDone = { onSignUpClick() }
                ),
                singleLine = true
            )

            Button(
                onClick = onSignUpClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .safeDrawingPadding(),
                enabled = state !is AuthState.Loading
            ) {
                Text(
                    text = stringResource(id = R.string.sign_up)
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
