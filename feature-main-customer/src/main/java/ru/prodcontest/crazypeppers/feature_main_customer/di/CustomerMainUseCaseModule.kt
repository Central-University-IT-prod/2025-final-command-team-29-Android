package ru.prodcontest.crazypeppers.feature_main_customer.di

import org.koin.dsl.module
import ru.prodcontest.crazypeppers.feature_main_customer.domain.usecase.GetPartnerPromosUseCase
import ru.prodcontest.crazypeppers.feature_main_customer.domain.usecase.GetPartnersUseCase
import ru.prodcontest.crazypeppers.feature_main_customer.domain.usecase.GetPromoUseCase
import ru.prodcontest.crazypeppers.feature_main_customer.domain.usecase.LogoutUseCase

val customerMainUseCaseModule = module {
    factory { GetPartnerPromosUseCase(get()) }
    factory { GetPartnersUseCase(get()) }
    factory { GetPromoUseCase(get()) }
    factory { LogoutUseCase(get()) }
}
