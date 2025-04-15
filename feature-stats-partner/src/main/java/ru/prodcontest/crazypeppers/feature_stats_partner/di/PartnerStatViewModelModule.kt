package ru.prodcontest.crazypeppers.feature_stats_partner.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.prodcontest.crazypeppers.feature_stats_partner.ui.viewmodel.PartnerStatsViewModel

val partnerStatViewModelModule = module {
    viewModel { PartnerStatsViewModel(get(), get()) }
}