package ru.prodcontest.crazypeppers.feature_auth_partner.domain.usecase

import ru.prodcontest.crazypeppers.common.domain.model.SignInSignUpModel
import ru.prodcontest.crazypeppers.feature_auth_partner.domain.repository.PartnerAuthRepository

class PartnerSaveLoginUseCase(private val repository: PartnerAuthRepository) {
    suspend operator fun invoke(data: SignInSignUpModel) {
        repository.saveLogin(data)
    }
}
