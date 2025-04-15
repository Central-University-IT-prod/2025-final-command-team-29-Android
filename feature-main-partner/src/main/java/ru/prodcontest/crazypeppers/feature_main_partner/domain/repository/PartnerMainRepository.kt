package ru.prodcontest.crazypeppers.feature_main_partner.domain.repository

import ru.prodcontest.crazypeppers.feature_main_partner.domain.model.PartnerPromoData

interface PartnerMainRepository {
    suspend fun getPartnerPromos(partnerId: String): List<PartnerPromoData>
    suspend fun createPromo(promo: PartnerPromoData): PartnerPromoData
    suspend fun deletePromo(promoId: String)
    suspend fun getPromo(promoId: String): PartnerPromoData
}
