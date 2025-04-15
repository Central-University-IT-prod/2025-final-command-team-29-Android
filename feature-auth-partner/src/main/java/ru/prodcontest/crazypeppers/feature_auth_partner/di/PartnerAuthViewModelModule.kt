package ru.prodcontest.crazypeppers.feature_auth_partner.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.prodcontest.crazypeppers.feature_auth_partner.ui.viewmodel.PartnerSignInViewModel
import ru.prodcontest.crazypeppers.feature_auth_partner.ui.viewmodel.PartnerSignUpViewModel

val partnerAuthViewModelModule = module {
    viewModel { PartnerSignInViewModel(get(), get()) }
    viewModel { PartnerSignUpViewModel(get(), get()) }
}
