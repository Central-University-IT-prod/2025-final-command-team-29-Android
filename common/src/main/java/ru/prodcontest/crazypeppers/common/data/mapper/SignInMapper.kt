package ru.prodcontest.crazypeppers.common.data.mapper

import ru.prodcontest.crazypeppers.common.data.network.dto.SignInRequest
import ru.prodcontest.crazypeppers.common.domain.model.SignIn

fun SignIn.toDto() = SignInRequest(
    phoneNumber = "+$phoneNumber",
    password = password
)
