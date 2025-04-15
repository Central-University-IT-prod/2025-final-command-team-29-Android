package ru.prodcontest.crazypeppers.feature_main_customer.domain.state

import ru.prodcontest.crazypeppers.feature_main_customer.domain.model.PartnerData

sealed class PartnersState {
    data object Idle : PartnersState()
    data object Loading : PartnersState()
    data class Success(val promos: List<PartnerData>) : PartnersState()
    data class Error(val message: String) : PartnersState()
}