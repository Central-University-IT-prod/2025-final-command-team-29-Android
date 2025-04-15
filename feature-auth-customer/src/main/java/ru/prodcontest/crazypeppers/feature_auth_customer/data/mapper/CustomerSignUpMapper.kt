package ru.prodcontest.crazypeppers.feature_auth_customer.data.mapper

import ru.prodcontest.crazypeppers.feature_auth_customer.data.network.dto.CustomerSignUpRequest
import ru.prodcontest.crazypeppers.feature_auth_customer.domain.model.CustomerSignUp

fun CustomerSignUp.toDto() = CustomerSignUpRequest(
    name = name,
    phoneNumber = "+$phoneNumber",
    password = password
)
