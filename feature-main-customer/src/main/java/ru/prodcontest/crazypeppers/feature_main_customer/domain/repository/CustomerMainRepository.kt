package ru.prodcontest.crazypeppers.feature_main_customer.domain.repository

import ru.prodcontest.crazypeppers.feature_main_customer.domain.model.CustomerPromoData
import ru.prodcontest.crazypeppers.feature_main_customer.domain.model.PartnerData

interface CustomerMainRepository {
    suspend fun getPartners(): List<PartnerData>
    suspend fun getPartnerPromos(partnerId: String): List<CustomerPromoData>
    suspend fun getPromo(promoId: String, partnerId: String) : CustomerPromoData
}