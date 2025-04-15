package ru.prodcontest.crazypeppers.feature_stats_partner.data.repository

import okhttp3.ResponseBody
import ru.prodcontest.crazypeppers.common.data.repository.BaseRepository
import ru.prodcontest.crazypeppers.feature_stats_partner.data.network.api.PartnerStatApi
import ru.prodcontest.crazypeppers.feature_stats_partner.domain.model.PartnerStatData
import ru.prodcontest.crazypeppers.feature_stats_partner.domain.repository.PartnerStatRepository
import ru.prodcontest.crazypeppers.feature_stats_partner.data.mapper.toModel

class IPartnerPartnerStatRepository(
    private val api: PartnerStatApi
) : PartnerStatRepository, BaseRepository() {
    override suspend fun getStat(partnerId: String): PartnerStatData = safeApiCall(
        apiCall = { api.getStat(partnerId = partnerId) },
        transform = { it.toModel() }
    )

    override suspend fun getStatFile(partnerId: String): ResponseBody = safeApiCall(
        apiCall = { api.getStatFile(partnerId = partnerId) },
        transform = { it }
    )
}
