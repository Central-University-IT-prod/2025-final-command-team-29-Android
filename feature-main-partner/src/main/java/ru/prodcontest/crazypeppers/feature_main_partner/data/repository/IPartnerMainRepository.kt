package ru.prodcontest.crazypeppers.feature_main_partner.data.repository

import ru.prodcontest.crazypeppers.common.data.repository.BaseRepository
import ru.prodcontest.crazypeppers.feature_main_partner.data.mapper.toDto
import ru.prodcontest.crazypeppers.feature_main_partner.data.mapper.toModel
import ru.prodcontest.crazypeppers.feature_main_partner.data.network.api.PartnerMainApi
import ru.prodcontest.crazypeppers.feature_main_partner.domain.model.PartnerPromoData
import ru.prodcontest.crazypeppers.feature_main_partner.domain.repository.PartnerMainRepository

class IPartnerMainRepository(
    private val api: PartnerMainApi
) : PartnerMainRepository, BaseRepository() {

    override suspend fun getPartnerPromos(partnerId: String): List<PartnerPromoData> = safeApiCall(
        apiCall = { api.getPartnerPromos() },
        transform = { promos -> promos.map { it.toModel() } }
    )

    override suspend fun createPromo(
        promo: PartnerPromoData
    ): PartnerPromoData = safeApiCall(
        apiCall = { api.createPromo(promo.toDto()) },
        transform = { it.toModel() }
    )

    override suspend fun deletePromo(promoId: String) = safeApiCallNoBody(
        apiCall = { api.deletePromo(promoId) }
    )

    override suspend fun getPromo(promoId: String): PartnerPromoData = safeApiCall(
        apiCall = { api.getPromo(promoId) },
        transform = { it.toModel() }
    )
}
