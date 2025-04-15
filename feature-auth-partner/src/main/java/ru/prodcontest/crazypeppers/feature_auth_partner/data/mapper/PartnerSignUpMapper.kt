package ru.prodcontest.crazypeppers.feature_auth_partner.data.mapper

import ru.prodcontest.crazypeppers.feature_auth_partner.data.network.dto.PartnerSignUpRequest
import ru.prodcontest.crazypeppers.feature_auth_partner.domain.model.PartnerSignUp

fun PartnerSignUp.toDto() = PartnerSignUpRequest(
    title = title,
    description = description,
    phoneNumber = "+$phoneNumber",
    password = password,
    imageId = imageId
)
