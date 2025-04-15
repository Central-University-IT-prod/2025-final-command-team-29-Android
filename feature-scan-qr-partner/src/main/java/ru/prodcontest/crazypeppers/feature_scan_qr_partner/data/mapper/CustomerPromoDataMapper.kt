package ru.prodcontest.crazypeppers.feature_scan_qr_partner.data.mapper

import ru.prodcontest.crazypeppers.feature_scan_qr_partner.data.network.dto.ConditionDtoType
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.data.network.dto.CustomerPromoDataDto
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.model.CustomerPromoData
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.model.CustomerPromoType

fun CustomerPromoDataDto.toModel() = CustomerPromoData(
    id = id,
    title = title,
    description = description ?: "",
    condition = conditionString,
    timeLimitStart = timeLimitStart,
    timeLimitEnd = timeLimitEnd,
    usageLimit = usageLimit,
    imageId = imageId,
    type = when (condition.type) {
        ConditionDtoType.COMMON -> CustomerPromoType.Common
        ConditionDtoType.UNIQUE -> CustomerPromoType.Unique
        ConditionDtoType.BUNDLE -> CustomerPromoType.Bundle(useOnTime = condition.buy ?: 0, current = current ?: 0)
    }
)