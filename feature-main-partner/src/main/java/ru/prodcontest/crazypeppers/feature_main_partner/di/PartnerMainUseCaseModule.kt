package ru.prodcontest.crazypeppers.feature_main_partner.di

import org.koin.dsl.module
import ru.prodcontest.crazypeppers.feature_main_partner.domain.usecase.CreatePromoUseCase
import ru.prodcontest.crazypeppers.feature_main_partner.domain.usecase.DeletePromoUseCase
import ru.prodcontest.crazypeppers.feature_main_partner.domain.usecase.GetPartnerPromosUseCase
import ru.prodcontest.crazypeppers.feature_main_partner.domain.usecase.GetPromoUseCase
import ru.prodcontest.crazypeppers.feature_main_partner.domain.usecase.LogoutUseCase

val partnerMainUseCaseModule = module {
    factory { GetPartnerPromosUseCase(get(), get()) }
    factory { CreatePromoUseCase(get()) }
    factory { DeletePromoUseCase(get()) }
    factory { GetPromoUseCase(get()) }
    factory { LogoutUseCase(get()) }
}
