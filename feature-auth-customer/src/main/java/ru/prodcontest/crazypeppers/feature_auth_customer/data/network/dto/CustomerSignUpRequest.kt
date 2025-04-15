package ru.prodcontest.crazypeppers.feature_auth_customer.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerSignUpRequest(
    val name: String,
    @SerialName("phone_number") val phoneNumber: String,
    val password: String
)
