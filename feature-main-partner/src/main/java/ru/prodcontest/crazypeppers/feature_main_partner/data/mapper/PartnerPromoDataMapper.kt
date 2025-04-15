package ru.prodcontest.crazypeppers.feature_main_partner.data.mapper

import ru.prodcontest.crazypeppers.feature_main_partner.data.network.dto.ConditionDto
import ru.prodcontest.crazypeppers.feature_main_partner.data.network.dto.ConditionDtoType
import ru.prodcontest.crazypeppers.feature_main_partner.data.network.dto.PartnerCreatePromoDataDto
import ru.prodcontest.crazypeppers.feature_main_partner.data.network.dto.PartnerPromoDataDto
import ru.prodcontest.crazypeppers.feature_main_partner.domain.model.PartnerPromoData
import ru.prodcontest.crazypeppers.feature_main_partner.domain.model.PartnerPromoType

fun PartnerPromoDataDto.toModel() = PartnerPromoData(
    id = id,
    title = title,
    description = description ?: "",
    condition = conditionString,
    timeLimitStart = timeLimitStart,
    timeLimitEnd = timeLimitEnd,
    usageLimit = usageLimit,
    type = when (condition.type) {
        ConditionDtoType.COMMON -> PartnerPromoType.Common
        ConditionDtoType.UNIQUE -> PartnerPromoType.Unique
        ConditionDtoType.BUNDLE -> PartnerPromoType.Bundle(useOnTime = condition.buy ?: 0)
    },
    imageId = imageId
)

fun PartnerPromoData.toDto() = PartnerCreatePromoDataDto(
    title = title,
    description = description,
    condition = ConditionDto(
        type = when(type) {
            PartnerPromoType.Common -> ConditionDtoType.COMMON
            PartnerPromoType.Unique -> ConditionDtoType.UNIQUE
            is PartnerPromoType.Bundle -> ConditionDtoType.BUNDLE
        },
        buy = if (type is PartnerPromoType.Bundle) type.useOnTime else null
    ),
    timeLimitStart = timeLimitStart,
    timeLimitEnd = timeLimitEnd,
    usageLimit = usageLimit,
    conditionString = condition,
    imageId = imageId
)
