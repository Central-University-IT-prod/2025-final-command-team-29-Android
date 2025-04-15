package ru.prodcontest.crazypeppers.feature_auth_customer.data.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.prodcontest.crazypeppers.common.data.network.dto.SignInRequest
import ru.prodcontest.crazypeppers.common.data.network.dto.SignInSignUpResponse
import ru.prodcontest.crazypeppers.feature_auth_customer.data.network.dto.CustomerSignUpRequest

interface CustomerAuthApi {

    @POST("clients/auth/sign-in")
    suspend fun signIn(@Body request: SignInRequest): Response<SignInSignUpResponse>

    @POST("clients/auth/sign-up")
    suspend fun signUp(@Body request: CustomerSignUpRequest): Response<SignInSignUpResponse>
}
