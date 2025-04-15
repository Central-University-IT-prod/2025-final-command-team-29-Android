package ru.prodcontest.crazypeppers.feature_auth_customer.di

import org.koin.dsl.module
import retrofit2.Retrofit
import ru.prodcontest.crazypeppers.common.di.util.bind
import ru.prodcontest.crazypeppers.feature_auth_customer.data.network.api.CustomerAuthApi

val customerAuthNetworkModule = module {
    factory {
        get<Retrofit>().bind<CustomerAuthApi>()
    }
}
