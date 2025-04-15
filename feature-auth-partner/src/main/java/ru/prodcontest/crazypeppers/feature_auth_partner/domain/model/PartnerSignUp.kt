package ru.prodcontest.crazypeppers.feature_auth_partner.domain.model

data class PartnerSignUp(
    val title: String,
    val description: String,
    val imageId: Int,
    val phoneNumber: String,
    val password: String
)
