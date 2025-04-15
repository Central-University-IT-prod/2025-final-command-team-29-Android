package ru.prodcontest.crazypeppers.feature_scan_qr_partner.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.ui.viewmodel.PartnerScanQrScreenViewModel

val scanQrViewModelModule = module {
    viewModel { PartnerScanQrScreenViewModel(get(), get()) }
}