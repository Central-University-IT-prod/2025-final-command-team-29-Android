package ru.prodcontest.crazypeppers.feature_auth_customer.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.prodcontest.crazypeppers.feature_auth_customer.domain.state.AuthState
import ru.prodcontest.crazypeppers.feature_auth_customer.domain.usecase.CustomerSaveLoginUseCase
import ru.prodcontest.crazypeppers.feature_auth_customer.domain.usecase.CustomerSignInUseCase

class CustomerSignInViewModel(
    private val customerSignInUseCase: CustomerSignInUseCase,
    private val customerSaveLoginUseCase: CustomerSaveLoginUseCase
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
                    customerSignInUseCase(_phoneNumber.value, _password.value)
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
            val data = (state.value as? AuthState.SignInSuccess)?.data
            Log.d("sdfsdfsdgdfg", "onSignInSuccess: $data")
            if (data != null) {
                customerSaveLoginUseCase(data)
            }
        }
    }
}
