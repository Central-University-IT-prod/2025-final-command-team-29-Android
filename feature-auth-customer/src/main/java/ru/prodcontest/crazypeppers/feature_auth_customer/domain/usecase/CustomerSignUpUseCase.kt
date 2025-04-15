package ru.prodcontest.crazypeppers.feature_auth_customer.domain.usecase

import android.util.Log
import ru.prodcontest.crazypeppers.feature_auth_customer.domain.model.CustomerSignUp
import ru.prodcontest.crazypeppers.feature_auth_customer.domain.repository.CustomerAuthRepository
import ru.prodcontest.crazypeppers.feature_auth_customer.domain.state.AuthState

class CustomerSignUpUseCase(private val repository: CustomerAuthRepository) {

    suspend operator fun invoke(
        name: String,
        phoneNumber: String,
        password: String
    ): Result<AuthState> = try {
        val credentials = CustomerSignUp(name, phoneNumber, password)
        val result = repository.signUp(credentials)
        Log.d(TAG, "invoke: $result")
        Result.success(AuthState.SignUpSuccess(result))
    } catch (e: Exception) {
        Log.e(TAG, "invoke: ", e)
        Result.failure(e)
    }

    companion object {
        private const val TAG = "CustomerSignUpUseCase"
    }
}
