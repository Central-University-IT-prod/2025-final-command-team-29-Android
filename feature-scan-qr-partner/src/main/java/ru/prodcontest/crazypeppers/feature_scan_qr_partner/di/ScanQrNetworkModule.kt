package ru.prodcontest.crazypeppers.feature_scan_qr_partner.di

import org.koin.dsl.module
import retrofit2.Retrofit
import ru.prodcontest.crazypeppers.common.di.util.bind
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.data.network.api.ScanQrApi
val scanQrNetworkModule = module {
    factory {
        get<Retrofit>().bind<ScanQrApi>()
    }
}