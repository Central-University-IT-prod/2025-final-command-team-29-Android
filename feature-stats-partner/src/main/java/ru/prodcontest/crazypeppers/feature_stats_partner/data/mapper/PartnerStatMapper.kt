package ru.prodcontest.crazypeppers.feature_stats_partner.data.mapper

import ru.prodcontest.crazypeppers.feature_stats_partner.data.network.dto.PartnerStatDto
import ru.prodcontest.crazypeppers.feature_stats_partner.domain.model.PartnerStatData

fun PartnerStatDto.toModel() = PartnerStatData(
    activeUsers = activeUsers,
    dailyUsers = dailyUsers,
    conversion = conversion,
    buyCount = buyCount,
    dailyBuys = dailyBuys,
    activePromos = activePromos
)