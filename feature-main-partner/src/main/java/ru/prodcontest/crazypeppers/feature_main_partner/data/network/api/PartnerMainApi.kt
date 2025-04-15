package ru.prodcontest.crazypeppers.feature_main_partner.data.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.prodcontest.crazypeppers.feature_main_partner.data.network.dto.PartnerCreatePromoDataDto
import ru.prodcontest.crazypeppers.feature_main_partner.data.network.dto.PartnerPromoDataDto

interface PartnerMainApi {

    @GET("promos")
    suspend fun getPartnerPromos(): Response<List<PartnerPromoDataDto>>

    @POST("promos")
    suspend fun createPromo(@Body promo: PartnerCreatePromoDataDto): Response<PartnerPromoDataDto>

    @DELETE("promos/{promoId}")
    suspend fun deletePromo(@Path("promoId") promoId: String): Response<Unit>

    @GET("promos/{promoId}")
    suspend fun getPromo(@Path("promoId") promoId: String): Response<PartnerPromoDataDto>
}
