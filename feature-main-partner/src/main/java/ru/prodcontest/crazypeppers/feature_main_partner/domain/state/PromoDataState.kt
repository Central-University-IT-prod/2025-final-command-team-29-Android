package ru.prodcontest.crazypeppers.feature_main_partner.domain.state

import ru.prodcontest.crazypeppers.feature_main_partner.domain.model.PartnerPromoData

sealed class PromoDataState {
    data object Idle : PromoDataState()
    data object Loading : PromoDataState()
    data class Success(val data: PartnerPromoData) : PromoDataState()
    data class Error(val message: String) : PromoDataState()
}