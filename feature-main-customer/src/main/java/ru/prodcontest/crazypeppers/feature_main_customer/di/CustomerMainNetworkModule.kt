package ru.prodcontest.crazypeppers.feature_main_customer.di

import org.koin.dsl.module
import retrofit2.Retrofit
import ru.prodcontest.crazypeppers.common.di.util.bind
import ru.prodcontest.crazypeppers.feature_main_customer.data.network.api.CustomerMainApi

val customerMainNetworkModule = module {
    factory {
        get<Retrofit>().bind<CustomerMainApi>()
    }
}
