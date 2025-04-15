package ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.state

import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.model.CustomerPromoData

sealed class ScanQrState {
    data object Scanning : ScanQrState()
    data object Processing : ScanQrState()
    data class Success(val promoData: CustomerPromoData): ScanQrState()
    data class Error(val message: String) : ScanQrState()
}