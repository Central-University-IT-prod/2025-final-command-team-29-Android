package ru.prodcontest.crazypeppers.feature_stats_partner.domain.stat

import ru.prodcontest.crazypeppers.feature_stats_partner.domain.model.PartnerStatData

sealed class PartnerStatsState {
    data object Idle : PartnerStatsState()
    data object Loading : PartnerStatsState()
    data class Success(val data: PartnerStatData): PartnerStatsState()
    data class Error(val message: String) : PartnerStatsState()
}