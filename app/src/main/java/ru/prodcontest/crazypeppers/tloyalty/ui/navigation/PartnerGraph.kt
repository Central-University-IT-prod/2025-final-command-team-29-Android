package ru.prodcontest.crazypeppers.tloyalty.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class PartnerGraph {

    @Serializable
    data object Welcome : PartnerGraph()

    @Serializable
    data object Main : PartnerGraph()

    @Serializable
    data object CreatePromoData : PartnerGraph()

    @Serializable
    data class PromoData(val promoId: String) : PartnerGraph()

    @Serializable
    data object ScanQr : PartnerGraph()

    @Serializable
    data object Stats : PartnerGraph()
}
