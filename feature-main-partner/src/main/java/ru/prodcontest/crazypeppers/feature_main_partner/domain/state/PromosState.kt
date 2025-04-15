package ru.prodcontest.crazypeppers.feature_main_partner.domain.state

import ru.prodcontest.crazypeppers.feature_main_partner.domain.model.PartnerPromoData

sealed class PromosState {
    data object Idle : PromosState()
    data object Loading : PromosState()
    data class GetPartnerPromosSuccess(val promos: List<PartnerPromoData>) : PromosState()
    data class CreatePromoSuccess(val promo: PartnerPromoData) : PromosState()
    data class DeletePromoSuccess(val promoId: String) : PromosState()
    data class Error(val message: String) : PromosState()
}
