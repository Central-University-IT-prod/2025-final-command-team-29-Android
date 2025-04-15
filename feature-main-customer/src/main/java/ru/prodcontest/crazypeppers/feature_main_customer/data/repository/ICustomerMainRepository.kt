package ru.prodcontest.crazypeppers.feature_main_customer.data.repository

import ru.prodcontest.crazypeppers.feature_main_customer.data.mapper.toModel
import ru.prodcontest.crazypeppers.feature_main_customer.data.network.api.CustomerMainApi
import ru.prodcontest.crazypeppers.feature_main_customer.domain.model.CustomerPromoData
import ru.prodcontest.crazypeppers.feature_main_customer.domain.model.PartnerData
import ru.prodcontest.crazypeppers.feature_main_customer.domain.repository.CustomerMainRepository
import ru.prodcontest.crazypeppers.common.data.repository.BaseRepository

class ICustomerMainRepository(
    private val api: CustomerMainApi
) : CustomerMainRepository, BaseRepository() {

    override suspend fun getPartners(): List<PartnerData> = safeApiCall(
        apiCall = { api.getPartners() },
        transform = { partners -> partners.map { it.toModel() } }
    )

    override suspend fun getPartnerPromos(partnerId: String): List<CustomerPromoData> = safeApiCall(
        apiCall = { api.getPartnerPromos(partnerId) },
        transform = { promos -> promos.map { it.toModel() } }
    )

    override suspend fun getPromo(promoId: String, partnerId: String): CustomerPromoData = safeApiCall(
        apiCall = { api.getPartnerPromo(promoId = promoId, partnerId = partnerId) },
        transform = { it.toModel() }
    )
}
