package ru.prodcontest.crazypeppers.common.data.mapper

import ru.prodcontest.crazypeppers.common.data.network.dto.SignInSignUpResponse
import ru.prodcontest.crazypeppers.common.domain.model.SignInSignUpModel

fun SignInSignUpResponse.toModel() = SignInSignUpModel(
    id = id,
    token = token
)
