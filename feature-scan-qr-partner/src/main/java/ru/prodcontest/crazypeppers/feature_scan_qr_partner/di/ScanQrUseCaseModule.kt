package ru.prodcontest.crazypeppers.feature_scan_qr_partner.di

import org.koin.dsl.module
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.usecase.ActivatePromoUseCase
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.usecase.GetPromoUseCase

val scanQrUseCaseModule = module {
    factory() { GetPromoUseCase(get()) }
    factory() { ActivatePromoUseCase(get()) }
}