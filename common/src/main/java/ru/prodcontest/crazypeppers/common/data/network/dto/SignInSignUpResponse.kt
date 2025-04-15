package ru.prodcontest.crazypeppers.common.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class SignInSignUpResponse(
    val id: String,
    val token: String
)
