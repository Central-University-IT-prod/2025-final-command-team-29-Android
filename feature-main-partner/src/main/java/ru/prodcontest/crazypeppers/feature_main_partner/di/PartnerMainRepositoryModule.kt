package ru.prodcontest.crazypeppers.feature_main_partner.di

import org.koin.dsl.module
import ru.prodcontest.crazypeppers.feature_main_partner.data.repository.IPartnerMainRepository
import ru.prodcontest.crazypeppers.feature_main_partner.domain.repository.PartnerMainRepository

val partnerMainRepositoryModule = module {
    factory<PartnerMainRepository> { IPartnerMainRepository(get()) }
}
