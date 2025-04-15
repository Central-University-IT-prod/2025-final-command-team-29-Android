package ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PromoToActivate(
    @SerialName("client_id") val customerId: String,
    @SerialName("promo_id") val promoId: String
)
