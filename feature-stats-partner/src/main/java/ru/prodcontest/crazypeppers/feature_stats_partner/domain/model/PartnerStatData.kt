package ru.prodcontest.crazypeppers.feature_stats_partner.domain.model

data class PartnerStatData(
    val activeUsers: Int,
    val dailyUsers: Int,
    val conversion: Float,
    val buyCount: Int,
    val dailyBuys: Int,
    val activePromos: Int,
)