package ru.prodcontest.crazypeppers.feature_auth_customer.domain.repository

import ru.prodcontest.crazypeppers.common.domain.model.SignIn
import ru.prodcontest.crazypeppers.common.domain.model.SignInSignUpModel
import ru.prodcontest.crazypeppers.feature_auth_customer.domain.model.CustomerSignUp

interface CustomerAuthRepository {
    suspend fun signIn(credentials: SignIn): SignInSignUpModel
    suspend fun signUp(credentials: CustomerSignUp): SignInSignUpModel
    suspend fun saveLogin(data: SignInSignUpModel)
}
