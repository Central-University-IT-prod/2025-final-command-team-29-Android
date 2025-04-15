package ru.prodcontest.crazypeppers.feature_auth_partner.di

import org.koin.dsl.module
import ru.prodcontest.crazypeppers.feature_auth_partner.data.repository.IPartnerAuthRepository
import ru.prodcontest.crazypeppers.feature_auth_partner.domain.repository.PartnerAuthRepository

val partnerAuthRepositoryModule = module {
    factory<PartnerAuthRepository> { IPartnerAuthRepository(get(), get()) }
}
