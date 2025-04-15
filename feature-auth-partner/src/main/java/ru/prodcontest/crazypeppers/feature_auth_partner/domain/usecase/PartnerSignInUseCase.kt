package ru.prodcontest.crazypeppers.feature_auth_partner.domain.usecase

import android.util.Log
import ru.prodcontest.crazypeppers.common.domain.model.SignIn
import ru.prodcontest.crazypeppers.feature_auth_partner.domain.repository.PartnerAuthRepository
import ru.prodcontest.crazypeppers.feature_auth_partner.domain.state.AuthState

class PartnerSignInUseCase(private val repository: PartnerAuthRepository) {

    suspend operator fun invoke(phoneNumber: String, password: String): Result<AuthState> = try {
        val credentials = SignIn(phoneNumber, password)
        val result = repository.signIn(credentials)
        Log.d(TAG, "invoke: $result")
        Result.success(AuthState.SignInSuccess(result))
    } catch (e: Exception) {
        Log.e(TAG, "invoke: ", e)
        Result.failure(e)
    }

    companion object {
        private const val TAG = "PartnerSignInUseCase"
    }
}
