package ru.prodcontest.crazypeppers.feature_stats_partner.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PartnerStatDto(
    @SerialName("active_users_count") val activeUsers: Int,
    @SerialName("daily_active_users_count") val dailyUsers: Int,

    @SerialName("conversion_rate") val conversion: Float,
    @SerialName("buy_promos_count") val buyCount: Int,

    @SerialName("daily_buy_promo_count") val dailyBuys: Int,
    @SerialName("active_promos_count") val activePromos: Int,
)