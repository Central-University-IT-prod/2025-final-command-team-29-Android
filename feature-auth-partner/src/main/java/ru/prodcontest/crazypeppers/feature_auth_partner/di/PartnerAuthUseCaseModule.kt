package ru.prodcontest.crazypeppers.feature_auth_partner.di

import org.koin.dsl.module
import ru.prodcontest.crazypeppers.feature_auth_partner.domain.usecase.PartnerSaveLoginUseCase
import ru.prodcontest.crazypeppers.feature_auth_partner.domain.usecase.PartnerSignInUseCase
import ru.prodcontest.crazypeppers.feature_auth_partner.domain.usecase.PartnerSignUpUseCase

val partnerAuthUseCaseModule = module {
    factory { PartnerSignInUseCase(get()) }
    factory { PartnerSignUpUseCase(get()) }
    factory { PartnerSaveLoginUseCase(get()) }
}
