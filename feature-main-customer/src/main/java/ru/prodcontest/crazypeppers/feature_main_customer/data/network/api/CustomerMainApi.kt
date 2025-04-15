package ru.prodcontest.crazypeppers.feature_main_customer.data.network.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.prodcontest.crazypeppers.feature_main_customer.data.network.dto.CustomerPromoDataDto
import ru.prodcontest.crazypeppers.feature_main_customer.data.network.dto.PartnerDataDto

interface CustomerMainApi {

    @GET("partners")
    suspend fun getPartners(): Response<List<PartnerDataDto>>

    @GET("partners/{partnerId}/promos")
    suspend fun getPartnerPromos(@Path("partnerId") partnerId: String): Response<List<CustomerPromoDataDto>>

    @GET("partners/{partnerId}/promos/{promoId}")
    suspend fun getPartnerPromo(
        @Path("partnerId") partnerId: String,
        @Path("promoId") promoId: String
    ): Response<CustomerPromoDataDto>
}
