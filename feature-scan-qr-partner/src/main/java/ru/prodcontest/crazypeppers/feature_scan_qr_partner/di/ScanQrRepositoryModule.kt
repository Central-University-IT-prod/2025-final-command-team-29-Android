package ru.prodcontest.crazypeppers.feature_scan_qr_partner.di

import org.koin.dsl.module
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.data.repository.IScanQrRepository
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.repository.ScanQrRepository

val scanQrRepositoryModule = module {
    factory<ScanQrRepository> { IScanQrRepository(get()) }
}