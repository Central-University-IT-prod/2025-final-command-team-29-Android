package ru.prodcontest.crazypeppers.feature_main_customer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.prodcontest.crazypeppers.feature_main_customer.domain.state.PartnersState
import ru.prodcontest.crazypeppers.feature_main_customer.domain.usecase.GetPartnersUseCase
import ru.prodcontest.crazypeppers.feature_main_customer.domain.usecase.LogoutUseCase

class CustomerMainScreenViewModel(
    private val getPartnersUseCase: GetPartnersUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<PartnersState>(PartnersState.Idle)
    val state: StateFlow<PartnersState> get() = _state

    init {
        loadPartners()
    }

    fun logout() {
        logoutUseCase.invoke()
    }

    fun loadPartners() {
        viewModelScope.launch {
            _state.value = PartnersState.Loading

            runCatching {
                withContext(Dispatchers.IO) { getPartnersUseCase() }
            }.fold(
                onSuccess = { partners ->
                    _state.value = partners.getOrNull()
                        ?: PartnersState.Error("Failed to load partners")
                },
                onFailure = { e ->
                    _state.value =
                        PartnersState.Error(e.localizedMessage ?: "Unknown error")
                }
            )
        }
    }
}
