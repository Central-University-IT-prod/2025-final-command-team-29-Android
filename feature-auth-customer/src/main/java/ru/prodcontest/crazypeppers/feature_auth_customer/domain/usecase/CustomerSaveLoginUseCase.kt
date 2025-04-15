package ru.prodcontest.crazypeppers.feature_auth_customer.domain.usecase

import ru.prodcontest.crazypeppers.common.domain.model.SignInSignUpModel
import ru.prodcontest.crazypeppers.feature_auth_customer.domain.repository.CustomerAuthRepository

class CustomerSaveLoginUseCase(private val repository: CustomerAuthRepository) {

    suspend operator fun invoke(data: SignInSignUpModel) {
        repository.saveLogin(data)
    }

    companion object {
        private const val TAG = "CustomerSaveLoginUseCase"
    }
}
