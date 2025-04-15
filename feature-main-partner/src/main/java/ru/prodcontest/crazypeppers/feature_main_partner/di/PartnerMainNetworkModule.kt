package ru.prodcontest.crazypeppers.feature_main_partner.di

import org.koin.dsl.module
import retrofit2.Retrofit
import ru.prodcontest.crazypeppers.common.di.util.bind
import ru.prodcontest.crazypeppers.feature_main_partner.data.network.api.PartnerMainApi

val partnerMainNetworkModule = module {
    factory {
        get<Retrofit>().bind<PartnerMainApi>()
    }
}
