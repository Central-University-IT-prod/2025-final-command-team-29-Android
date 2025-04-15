package ru.prodcontest.crazypeppers.feature_auth_partner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.prodcontest.crazypeppers.feature_auth_partner.domain.state.AuthState
import ru.prodcontest.crazypeppers.feature_auth_partner.domain.usecase.PartnerSaveLoginUseCase
import ru.prodcontest.crazypeppers.feature_auth_partner.domain.usecase.PartnerSignUpUseCase

class PartnerSignUpViewModel(
    private val partnerSignUpUseCase: PartnerSignUpUseCase,
    private val partnerSaveLoginUseCase: PartnerSaveLoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> get() = _state

    val imageId = (0..4).random()

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> get() = _title

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> get() = _description

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> get() = _phoneNumber

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    fun onTitleChanged(name: String) {
        _title.value = name
    }

    fun onDescriptionChanged(description: String) {
        _description.value = description
    }

    fun onPhoneNumberChanged(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
    }

    fun onSignUpClicked() {
        viewModelScope.launch {
            _state.value = AuthState.Loading

            runCatching {
                withContext(Dispatchers.IO) {
                    partnerSignUpUseCase(
                        title = _title.value,
                        description = _description.value,
                        phoneNumber = _phoneNumber.value,
                        password = _password.value,
                        imageId = imageId
                    )
                }
            }.fold(
                onSuccess = { authState ->
                    _state.value = authState.getOrNull()
                        ?: AuthState.Error("Failed to sign up")
                },
                onFailure = { e ->
                    _state.value =
                        AuthState.Error(e.localizedMessage ?: "Unknown error")
                }
            )
        }
    }

    fun onSignUpSuccess() {
        viewModelScope.launch {
            val data = (_state.value as? AuthState.SignUpSuccess)?.data
            if (data != null) {
                partnerSaveLoginUseCase(data)
            }
        }
    }
}
