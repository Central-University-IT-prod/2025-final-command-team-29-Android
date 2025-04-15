package ru.prodcontest.crazypeppers.feature_main_partner.domain.model

data class PartnerPromoData(
    val id: String,
    val imageId: Int,
    val title: String,
    val description: String,
    val timeLimitStart: Long?,
    val timeLimitEnd: Long?,
    val usageLimit: Int?,
    val condition: String,
    val type: PartnerPromoType
)

sealed class PartnerPromoType {
    data object Common : PartnerPromoType()
    data object Unique : PartnerPromoType()
    data class Bundle(val useOnTime: Int) : PartnerPromoType()
}