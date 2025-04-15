package ru.prodcontest.crazypeppers.tloyalty.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class CustomerGraph {

    @Serializable
    data object Welcome : CustomerGraph()

    @Serializable
    data object Main : CustomerGraph()

    @Serializable
    data class PartnerPromos(val partnerTitle: String, val partnerId: String) : CustomerGraph()

    @Serializable
    data class PromoData(val partnerId: String, val promoId: String) : CustomerGraph()
}
