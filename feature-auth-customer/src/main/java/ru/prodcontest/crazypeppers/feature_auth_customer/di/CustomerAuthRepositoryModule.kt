package ru.prodcontest.crazypeppers.feature_auth_customer.di

import org.koin.dsl.module
import ru.prodcontest.crazypeppers.feature_auth_customer.data.repository.ICustomerAuthRepository
import ru.prodcontest.crazypeppers.feature_auth_customer.domain.repository.CustomerAuthRepository

val customerAuthRepositoryModule = module {
    factory<CustomerAuthRepository> { ICustomerAuthRepository(get(), get()) }
}
