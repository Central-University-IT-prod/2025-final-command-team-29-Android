package ru.prodcontest.crazypeppers.feature_stats_partner.di

import org.koin.dsl.module
import ru.prodcontest.crazypeppers.feature_stats_partner.data.repository.IPartnerPartnerStatRepository
import ru.prodcontest.crazypeppers.feature_stats_partner.domain.repository.PartnerStatRepository

val partnerStatRepositoryModule = module {
    factory<PartnerStatRepository> { IPartnerPartnerStatRepository(get()) }
}