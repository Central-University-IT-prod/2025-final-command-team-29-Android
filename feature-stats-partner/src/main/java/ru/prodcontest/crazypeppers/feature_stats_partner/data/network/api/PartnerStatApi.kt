package ru.prodcontest.crazypeppers.feature_stats_partner.data.network.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming
import ru.prodcontest.crazypeppers.feature_stats_partner.data.network.dto.PartnerStatDto

interface PartnerStatApi {

    @GET("partners/{partnerId}/stat")
    suspend fun getStat(@Path("partnerId") partnerId: String): Response<PartnerStatDto>

    @Streaming
    @GET("partners/{partnerId}/stat/export")
    suspend fun getStatFile(@Path("partnerId") partnerId: String): Response<ResponseBody>
}