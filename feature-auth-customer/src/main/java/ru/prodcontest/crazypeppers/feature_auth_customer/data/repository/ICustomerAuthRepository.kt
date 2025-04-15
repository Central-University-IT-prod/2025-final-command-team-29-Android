package ru.prodcontest.crazypeppers.feature_auth_customer.data.repository

import android.content.SharedPreferences
import ru.prodcontest.crazypeppers.common.data.mapper.toDto
import ru.prodcontest.crazypeppers.common.data.mapper.toModel
import ru.prodcontest.crazypeppers.common.data.repository.BaseRepository
import ru.prodcontest.crazypeppers.common.domain.model.SignIn
import ru.prodcontest.crazypeppers.common.domain.model.SignInSignUpModel
import ru.prodcontest.crazypeppers.feature_auth_customer.data.network.api.CustomerAuthApi
import ru.prodcontest.crazypeppers.feature_auth_customer.domain.model.CustomerSignUp
import ru.prodcontest.crazypeppers.feature_auth_customer.domain.repository.CustomerAuthRepository
import ru.prodcontest.crazypeppers.feature_auth_customer.data.mapper.toDto

class ICustomerAuthRepository(
    private val api: CustomerAuthApi,
    private val sharedPrefs: SharedPreferences
) : CustomerAuthRepository, BaseRepository() {

    override suspend fun signIn(credentials: SignIn): SignInSignUpModel = safeApiCall(
        apiCall = { api.signIn(credentials.toDto()) },
        transform = { it.toModel() }
    )

    override suspend fun signUp(credentials: CustomerSignUp): SignInSignUpModel = safeApiCall(
        apiCall = { api.signUp(credentials.toDto()) },
        transform = { it.toModel() }
    )

    override suspend fun saveLogin(data: SignInSignUpModel) {
        sharedPrefs.edit().putString("token", data.token).apply()
        sharedPrefs.edit().putString("type", "customer").apply()
        sharedPrefs.edit().putString("customerId", data.id).apply()
    }
}
