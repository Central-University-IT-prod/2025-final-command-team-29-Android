package ru.prodcontest.crazypeppers.feature_main_partner.domain.model

import android.graphics.Bitmap

data class ChangedPromoData(
    val title: String,
    val imageId: Int,
    val description: String,
    val timeLimitStart: Long?,
    val timeLimitEnd: Long?,
    val usageLimit: String?,
    val condition: String,
    val useOnTime: String?,
    val type: PartnerPromoType
) {
    companion object {
        fun empty(imageId: Int) = ChangedPromoData(
            title = "",
            description = "",
            timeLimitStart = null,
            timeLimitEnd = null,
            usageLimit = null,
            condition = "",
            type = PartnerPromoType.Common,
            useOnTime = null,
            imageId = imageId
        )
    }
}
