package ru.prodcontest.crazypeppers.feature_auth_partner.domain.usecase

import android.util.Log
import ru.prodcontest.crazypeppers.feature_auth_partner.domain.model.PartnerSignUp
import ru.prodcontest.crazypeppers.feature_auth_partner.domain.repository.PartnerAuthRepository
import ru.prodcontest.crazypeppers.feature_auth_partner.domain.state.AuthState

class PartnerSignUpUseCase(private val repository: PartnerAuthRepository) {

    suspend operator fun invoke(
        title: String,
        description: String,
        phoneNumber: String,
        password: String,
        imageId: Int,
    ): Result<AuthState> = try {
        val credentials = PartnerSignUp(title, description, imageId, phoneNumber, password)
        val result = repository.signUp(credentials)
        Log.d(TAG, "invoke: $result")
        Result.success(AuthState.SignUpSuccess(result))
    } catch (e: Exception) {
        Log.e(TAG, "invoke: ", e)
        Result.failure(e)
    }

    companion object {
        private const val TAG = "PartnerSignUpUseCase"
    }
}
