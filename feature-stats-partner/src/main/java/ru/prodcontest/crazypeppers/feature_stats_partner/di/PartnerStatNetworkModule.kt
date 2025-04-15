package ru.prodcontest.crazypeppers.feature_stats_partner.di

import org.koin.dsl.module
import retrofit2.Retrofit
import ru.prodcontest.crazypeppers.common.di.util.bind
import ru.prodcontest.crazypeppers.feature_stats_partner.data.network.api.PartnerStatApi

val partnerStatNetworkModule = module {
    factory {
        get<Retrofit>().bind<PartnerStatApi>()
    }
}