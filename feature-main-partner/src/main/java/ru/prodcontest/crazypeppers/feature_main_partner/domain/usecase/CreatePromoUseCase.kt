package ru.prodcontest.crazypeppers.feature_main_partner.domain.usecase

import ru.prodcontest.crazypeppers.feature_main_partner.domain.model.PartnerPromoData
import ru.prodcontest.crazypeppers.feature_main_partner.domain.repository.PartnerMainRepository

class CreatePromoUseCase(
    private val repository: PartnerMainRepository
) {
    suspend operator fun invoke(promo: PartnerPromoData): Result<PartnerPromoData> = try {
        val promo = repository.createPromo(promo)
        Result.success(promo)
    } catch (e: Exception) {
        Result.failure(e)
    }
}