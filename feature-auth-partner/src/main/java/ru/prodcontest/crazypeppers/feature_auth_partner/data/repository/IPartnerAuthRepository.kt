package ru.prodcontest.crazypeppers.feature_auth_partner.data.repository

import android.content.SharedPreferences
import ru.prodcontest.crazypeppers.common.data.mapper.toDto
import ru.prodcontest.crazypeppers.common.data.mapper.toModel
import ru.prodcontest.crazypeppers.common.data.repository.BaseRepository
import ru.prodcontest.crazypeppers.common.domain.model.SignIn
import ru.prodcontest.crazypeppers.common.domain.model.SignInSignUpModel
import ru.prodcontest.crazypeppers.feature_auth_partner.data.mapper.toDto
import ru.prodcontest.crazypeppers.feature_auth_partner.data.network.api.PartnerAuthApi
import ru.prodcontest.crazypeppers.feature_auth_partner.domain.model.PartnerSignUp
import ru.prodcontest.crazypeppers.feature_auth_partner.domain.repository.PartnerAuthRepository

class IPartnerAuthRepository(
    private val api: PartnerAuthApi,
    private val sharedPrefs: SharedPreferences
) : PartnerAuthRepository, BaseRepository() {

    override suspend fun signIn(credentials: SignIn): SignInSignUpModel = safeApiCall(
        apiCall = { api.signIn(credentials.toDto()) },
        transform = { it.toModel() }
    )

    override suspend fun signUp(credentials: PartnerSignUp): SignInSignUpModel = safeApiCall(
        apiCall = { api.signUp(credentials.toDto()) },
        transform = { it.toModel() }
    )

    override suspend fun saveLogin(data: SignInSignUpModel) {
        sharedPrefs.edit().putString("token", data.token).apply()
        sharedPrefs.edit().putString("type", "partner").apply()
        sharedPrefs.edit().putString("partnerId", data.id).apply()
    }
}
