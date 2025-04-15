package ru.prodcontest.crazypeppers.feature_main_partner.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.prodcontest.crazypeppers.feature_main_partner.ui.viewmodels.PartnerMainScreenViewModel
import ru.prodcontest.crazypeppers.feature_main_partner.ui.viewmodels.PartnerCreatePromoDataScreenViewModel
import ru.prodcontest.crazypeppers.feature_main_partner.ui.viewmodels.PartnerPromoDataScreenViewModel

val partnerMainViewModelModule = module {
    viewModel { PartnerMainScreenViewModel(get(), get(), get()) }
    viewModel { PartnerCreatePromoDataScreenViewModel(get()) }
    viewModel { PartnerPromoDataScreenViewModel(get()) }
}