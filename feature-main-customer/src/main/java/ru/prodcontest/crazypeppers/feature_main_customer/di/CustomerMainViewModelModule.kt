package ru.prodcontest.crazypeppers.feature_main_customer.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.prodcontest.crazypeppers.feature_main_customer.ui.viewmodels.CustomerMainScreenViewModel
import ru.prodcontest.crazypeppers.feature_main_customer.ui.viewmodels.CustomerPromoDataScreenViewModel
import ru.prodcontest.crazypeppers.feature_main_customer.ui.viewmodels.PartnerPromosScreenViewModel

val customerMainViewModelModule = module {
    viewModel { CustomerMainScreenViewModel(get(), get()) }
    viewModel { PartnerPromosScreenViewModel(get()) }
    viewModel { CustomerPromoDataScreenViewModel(get(), get()) }
}