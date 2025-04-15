package ru.prodcontest.crazypeppers.feature_main_customer.domain.state

import ru.prodcontest.crazypeppers.feature_main_customer.domain.model.CustomerPromoData

sealed class PromoDataState {
    data object Idle : PromoDataState()
    data object Loading : PromoDataState()
    data class Success(val data: CustomerPromoData) : PromoDataState()
    data class Error(val message: String) : PromoDataState()
}