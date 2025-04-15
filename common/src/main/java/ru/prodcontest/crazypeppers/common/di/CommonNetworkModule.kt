package ru.prodcontest.crazypeppers.common.di

import kotlinx.serialization.json.Json
import org.koin.dsl.module
import ru.prodcontest.crazypeppers.common.data.network.interceptor.AuthInterceptor
import ru.prodcontest.crazypeppers.common.di.util.provideOkHttpClient
import ru.prodcontest.crazypeppers.common.di.util.provideRetrofit

val commonNetworkModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
        }
    }

    single {
        provideRetrofit(
            baseUrl = "http://prod-team-29-4254c2ee.REDACTED/api/v1/",
            client = provideOkHttpClient(AuthInterceptor(get())),
            json = get()
        )
    }
}
