package ru.prodcontest.crazypeppers.feature_auth_partner.di

import org.koin.dsl.module
import retrofit2.Retrofit
import ru.prodcontest.crazypeppers.common.di.util.bind
import ru.prodcontest.crazypeppers.feature_auth_partner.data.network.api.PartnerAuthApi

val partnerAuthNetworkModule = module {
    factory {
        get<Retrofit>().bind<PartnerAuthApi>()
    }
}
