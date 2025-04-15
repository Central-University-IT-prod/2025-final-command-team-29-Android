package ru.prodcontest.crazypeppers.feature_auth_customer.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.prodcontest.crazypeppers.feature_auth_customer.ui.viewmodel.CustomerSignInViewModel
import ru.prodcontest.crazypeppers.feature_auth_customer.ui.viewmodel.CustomerSignUpViewModel

val customerAuthViewModelModule = module {
    viewModel { CustomerSignInViewModel(get(), get()) }
    viewModel { CustomerSignUpViewModel(get(), get()) }
}
