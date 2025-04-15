package ru.prodcontest.crazypeppers.feature_auth_partner.domain.repository

import ru.prodcontest.crazypeppers.common.domain.model.SignIn
import ru.prodcontest.crazypeppers.common.domain.model.SignInSignUpModel
import ru.prodcontest.crazypeppers.feature_auth_partner.domain.model.PartnerSignUp

interface PartnerAuthRepository {
    suspend fun signIn(credentials: SignIn): SignInSignUpModel
    suspend fun signUp(credentials: PartnerSignUp): SignInSignUpModel
    suspend fun saveLogin(data: SignInSignUpModel)
}
