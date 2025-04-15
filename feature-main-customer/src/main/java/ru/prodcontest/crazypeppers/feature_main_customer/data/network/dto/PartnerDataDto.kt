package ru.prodcontest.crazypeppers.feature_main_customer.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PartnerDataDto(
    @SerialName("image_id") val imageId: Int,
    val id: String,
    val title: String,
    val description: String
)
