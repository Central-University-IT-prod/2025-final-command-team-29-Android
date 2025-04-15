package ru.prodcontest.crazypeppers.feature_main_partner.domain.usecase

import ru.prodcontest.crazypeppers.feature_main_partner.domain.repository.PartnerMainRepository

class DeletePromoUseCase(
    private val repository: PartnerMainRepository
) {
    suspend operator fun invoke(promoIds: List<String>) = try {
        promoIds.forEach { promoId ->
            repository.deletePromo(promoId)
        }
    } catch (_: Exception) {}
}