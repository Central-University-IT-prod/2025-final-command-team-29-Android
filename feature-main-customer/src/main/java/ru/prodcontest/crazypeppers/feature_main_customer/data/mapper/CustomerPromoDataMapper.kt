package ru.prodcontest.crazypeppers.feature_main_customer.data.mapper

import ru.prodcontest.crazypeppers.feature_main_customer.data.network.dto.ConditionDtoType
import ru.prodcontest.crazypeppers.feature_main_customer.data.network.dto.CustomerPromoDataDto
import ru.prodcontest.crazypeppers.feature_main_customer.domain.model.CustomerPromoData
import ru.prodcontest.crazypeppers.feature_main_customer.domain.model.CustomerPromoType
import ru.prodcontest.crazypeppers.feature_main_customer.data.network.dto.ConditionDto

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

fun CustomerPromoData.toDto() = CustomerPromoDataDto(
    id = id,
    title = title,
    description = description,
    condition = ConditionDto(
        type = when (type) {
            CustomerPromoType.Common -> ConditionDtoType.COMMON
            CustomerPromoType.Unique -> ConditionDtoType.UNIQUE
            is CustomerPromoType.Bundle -> ConditionDtoType.BUNDLE
        },
        buy = if (type is CustomerPromoType.Bundle) type.useOnTime else null
    ),
    timeLimitStart = timeLimitStart,
    timeLimitEnd = timeLimitEnd,
    usageLimit = usageLimit,
    conditionString = condition,
    imageId = imageId,
    current = if (type is CustomerPromoType.Bundle) type.current else 0,
)
