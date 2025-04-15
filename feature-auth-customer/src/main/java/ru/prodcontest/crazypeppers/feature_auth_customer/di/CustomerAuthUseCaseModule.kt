package ru.prodcontest.crazypeppers.feature_auth_customer.di

import org.koin.dsl.module
import ru.prodcontest.crazypeppers.feature_auth_customer.domain.usecase.CustomerSaveLoginUseCase
import ru.prodcontest.crazypeppers.feature_auth_customer.domain.usecase.CustomerSignInUseCase
import ru.prodcontest.crazypeppers.feature_auth_customer.domain.usecase.CustomerSignUpUseCase

val customerAuthUseCaseModule = module {
    factory { CustomerSignInUseCase(get()) }
    factory { CustomerSignUpUseCase(get()) }
    factory { CustomerSaveLoginUseCase(get()) }
}
