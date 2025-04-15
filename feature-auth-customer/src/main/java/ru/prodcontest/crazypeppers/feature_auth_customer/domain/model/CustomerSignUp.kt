package ru.prodcontest.crazypeppers.feature_auth_customer.domain.model

data class CustomerSignUp(
    val name: String,
    val phoneNumber: String,
    val password: String
)
