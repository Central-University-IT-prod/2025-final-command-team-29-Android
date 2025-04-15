package ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.usecase

import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.repository.ScanQrRepository
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.state.ScanQrState

class GetPromoUseCase(
    private val repository: ScanQrRepository
) {
    suspend operator fun invoke(promoId: String, customerId: String): Result<ScanQrState> = try {
        val promo = repository.getPromo(promoId = promoId, customerId = customerId)
        Result.success(ScanQrState.Success(promo))
    } catch (e: Exception) {
        Result.failure(e)
    }
}