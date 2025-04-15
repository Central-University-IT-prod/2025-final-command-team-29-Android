package ru.prodcontest.crazypeppers.feature_stats_partner.domain.repository

import okhttp3.ResponseBody
import ru.prodcontest.crazypeppers.feature_stats_partner.domain.model.PartnerStatData

interface PartnerStatRepository {
    suspend fun getStat(partnerId: String): PartnerStatData

    suspend fun getStatFile(partnerId: String): ResponseBody
}