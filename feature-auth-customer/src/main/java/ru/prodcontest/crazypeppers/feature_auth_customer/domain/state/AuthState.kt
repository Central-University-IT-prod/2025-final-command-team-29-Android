package ru.prodcontest.crazypeppers.feature_auth_customer.domain.state

import ru.prodcontest.crazypeppers.common.domain.model.SignInSignUpModel

sealed class AuthState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data class SignInSuccess(val data: SignInSignUpModel) : AuthState()
    data class SignUpSuccess(val data: SignInSignUpModel) : AuthState()
    data class Error(val message: String) : AuthState()
}
