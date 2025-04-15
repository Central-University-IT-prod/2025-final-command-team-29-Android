package ru.prodcontest.crazypeppers.feature_main_customer.domain.state

import ru.prodcontest.crazypeppers.feature_main_customer.domain.model.CustomerPromoData

sealed class PromosState {
    data object Idle : PromosState()
    data object Loading : PromosState()
    data class Success(val promos: List<CustomerPromoData>) : PromosState()
    data class Error(val message: String) : PromosState()
}