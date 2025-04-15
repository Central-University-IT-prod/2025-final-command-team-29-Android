package ru.prodcontest.crazypeppers.feature_main_customer.domain.model

data class CustomerPromoData(
    val id: String,
    val imageId: Int,
    val title: String,
    val description: String,
    val timeLimitStart: Long?,
    val timeLimitEnd: Long?,
    val usageLimit: Int?,
    val condition: String,
    val type: CustomerPromoType
)

sealed class CustomerPromoType {
    data object Common : CustomerPromoType()
    data object Unique : CustomerPromoType()
    data class Bundle(val useOnTime: Int, val current: Int) : CustomerPromoType()
}
