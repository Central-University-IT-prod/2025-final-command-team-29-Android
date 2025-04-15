package ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.repository

import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.model.CustomerPromoData
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.model.PromoToActivate

interface ScanQrRepository {
    suspend fun getPromo(promoId: String, customerId: String) : CustomerPromoData
    suspend fun activatePromo(body: PromoToActivate)
}