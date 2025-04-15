package ru.prodcontest.crazypeppers.feature_main_customer.di

import org.koin.dsl.module
import ru.prodcontest.crazypeppers.feature_main_customer.data.repository.ICustomerMainRepository
import ru.prodcontest.crazypeppers.feature_main_customer.domain.repository.CustomerMainRepository

val customerMainRepositoryModule = module {
    factory<CustomerMainRepository> { ICustomerMainRepository(get()) }
}
