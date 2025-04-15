package ru.prodcontest.crazypeppers.feature_scan_qr_partner.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerPromoDataDto(
    @SerialName("promo_id") val id: String,
    @SerialName("image_id") val imageId: Int,
    val title: String,
    val description: String? = null,
    val condition: ConditionDto,
    val current: Int? = null,
    @SerialName("time_limit_start") val timeLimitStart: Long? = null,
    @SerialName("time_limit_end") val timeLimitEnd: Long? = null,
    @SerialName("usage_limit") val usageLimit: Int? = null,
    @SerialName("condition_string") val conditionString: String
)

@Serializable
data class ConditionDto(
    val type: ConditionDtoType,
    val buy: Int?
)

@Serializable
enum class ConditionDtoType {
    COMMON,
    UNIQUE,
    BUNDLE
}
