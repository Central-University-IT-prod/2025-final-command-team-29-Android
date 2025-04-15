package ru.prodcontest.crazypeppers.feature_auth_partner.data.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.prodcontest.crazypeppers.common.data.network.dto.SignInRequest
import ru.prodcontest.crazypeppers.common.data.network.dto.SignInSignUpResponse
import ru.prodcontest.crazypeppers.feature_auth_partner.data.network.dto.PartnerSignUpRequest

interface PartnerAuthApi {

    @POST("partners/auth/sign-in")
    suspend fun signIn(@Body request: SignInRequest): Response<SignInSignUpResponse>

    @POST("partners/auth/sign-up")
    suspend fun signUp(@Body request: PartnerSignUpRequest): Response<SignInSignUpResponse>
}
