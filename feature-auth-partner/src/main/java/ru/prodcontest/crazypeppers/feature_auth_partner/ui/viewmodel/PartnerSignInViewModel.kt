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
import ru.prodcontest.crazypeppers.feature_auth_partner.domain.usecase.PartnerSignInUseCase

class PartnerSignInViewModel(
    private val partnerSignInUseCase: PartnerSignInUseCase,
    private val partnerSaveLoginUseCase: PartnerSaveLoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> get() = _state

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> get() = _phoneNumber

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    fun onPhoneNumberChanged(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
    }

    fun onSignInClicked() {
        viewModelScope.launch {
            _state.value = AuthState.Loading

            runCatching {
                withContext(Dispatchers.IO) {
                    partnerSignInUseCase(_phoneNumber.value, _password.value)
                }
            }.fold(
                onSuccess = { authState ->
                    _state.value = authState.getOrNull()
                        ?: AuthState.Error("Failed to sign in")
                },
                onFailure = { e ->
                    _state.value =
                        AuthState.Error(e.localizedMessage ?: "Unknown error")
                }
            )
        }
    }

    fun onSignInSuccess() {
        viewModelScope.launch {
            val data = (_state.value as? AuthState.SignInSuccess)?.data
            if (data != null) {
                partnerSaveLoginUseCase(data)
            }
        }
    }
}
