package ru.prodcontest.crazypeppers.common.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    @SerialName("phone_number") val phoneNumber: String,
    val password: String
)
