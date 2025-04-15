package ru.prodcontest.crazypeppers.feature_scan_qr_partner.data.repository

import ru.prodcontest.crazypeppers.common.data.repository.BaseRepository
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.data.mapper.toModel
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.data.network.api.ScanQrApi
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.model.CustomerPromoData
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.model.PromoToActivate
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.repository.ScanQrRepository

class IScanQrRepository(
    private val api: ScanQrApi
) : ScanQrRepository, BaseRepository() {

    override suspend fun getPromo(
        promoId: String,
        customerId: String
    ): CustomerPromoData = safeApiCall(
        apiCall = { api.getPartners(promoId = promoId, customerId = customerId) },
        transform = { it.toModel() }
    )

    override suspend fun activatePromo(
        body: PromoToActivate
    ) = safeApiCallNoBody(
        apiCall = { api.activatePromo(body = body) },
    )
}
