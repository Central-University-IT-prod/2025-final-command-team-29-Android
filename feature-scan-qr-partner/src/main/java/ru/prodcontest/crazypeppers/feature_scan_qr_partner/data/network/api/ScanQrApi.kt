package ru.prodcontest.crazypeppers.feature_scan_qr_partner.data.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.data.network.dto.CustomerPromoDataDto
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.model.PromoToActivate

interface ScanQrApi {

    @GET("promos/{promoId}/{customerId}")
    suspend fun getPartners(@Path("promoId") promoId: String, @Path("customerId") customerId: String): Response<CustomerPromoDataDto>

    @POST("promos/activate")
    suspend fun activatePromo(@Body body: PromoToActivate): Response<Unit>
}