package ru.prodcontest.crazypeppers.feature_main_customer.data.mapper

import ru.prodcontest.crazypeppers.feature_main_customer.data.network.dto.PartnerDataDto
import ru.prodcontest.crazypeppers.feature_main_customer.domain.model.PartnerData

fun PartnerDataDto.toModel() = PartnerData(
    id = id,
    title = title,
    description = description,
    imageId = imageId
)