package ru.prodcontest.crazypeppers.feature_stats_partner.di

import org.koin.dsl.module
import ru.prodcontest.crazypeppers.feature_stats_partner.domain.usecase.GetPartnerStatFileUseCase
import ru.prodcontest.crazypeppers.feature_stats_partner.domain.usecase.GetPartnerStatUseCase

val partnerStatUseCaseModule = module {
    factory() { GetPartnerStatUseCase(get(), get()) }
    factory() { GetPartnerStatFileUseCase(get(), get()) }
}