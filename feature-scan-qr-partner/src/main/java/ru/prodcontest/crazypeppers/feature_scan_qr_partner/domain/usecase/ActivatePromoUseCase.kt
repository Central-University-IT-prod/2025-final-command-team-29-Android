package ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.usecase

import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.model.PromoToActivate
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.repository.ScanQrRepository

class ActivatePromoUseCase(
    private val repository: ScanQrRepository
) {
    suspend operator fun invoke(promoId: String, customerId: String) = try {
        repository.activatePromo(
            PromoToActivate(
                promoId = promoId,
                customerId = customerId
            )
        )
    } catch (_: Exception) { }
}