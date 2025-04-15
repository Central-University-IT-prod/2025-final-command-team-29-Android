package ru.prodcontest.crazypeppers.feature_auth_partner.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PartnerSignUpRequest(
    val title: String,
    @SerialName("image_id") val imageId: Int,
    val description: String,
    @SerialName("phone_number") val phoneNumber: String,
    val password: String
)
