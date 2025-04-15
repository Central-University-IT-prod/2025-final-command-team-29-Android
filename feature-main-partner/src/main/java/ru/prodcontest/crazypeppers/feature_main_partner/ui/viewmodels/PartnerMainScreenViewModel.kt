package ru.prodcontest.crazypeppers.feature_main_partner.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.prodcontest.crazypeppers.feature_main_partner.domain.state.PromosState
import ru.prodcontest.crazypeppers.feature_main_partner.domain.usecase.DeletePromoUseCase
import ru.prodcontest.crazypeppers.feature_main_partner.domain.usecase.GetPartnerPromosUseCase
import ru.prodcontest.crazypeppers.feature_main_partner.domain.usecase.LogoutUseCase
import java.text.SimpleDateFormat
import java.util.Locale

class PartnerMainScreenViewModel(
    private val getPartnerPromosUseCase: GetPartnerPromosUseCase,
    private val deletePromoUseCase: DeletePromoUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    private val _state = MutableStateFlow<PromosState>(PromosState.Idle)
    val state: StateFlow<PromosState> get() = _state
    private val _selectedIds = MutableStateFlow<List<String>>(emptyList())
    val selectedIds: StateFlow<List<String>> get() = _selectedIds

    fun logout() {
        logoutUseCase.invoke()
    }

    fun getTimeLimitText(startTime: Long?, endTime: Long?): String? {
        if (startTime != null && endTime != null) {
            return "${dateFormatter.format(startTime)}—${dateFormatter.format(endTime)}"
        }
        return null
    }

    fun changePromoSelected(promoId: String) {
        _selectedIds.value = _selectedIds.value.toMutableList().apply {
            if (promoId in _selectedIds.value) {
                remove(promoId)
            } else {
                add(promoId)
            }
        }
    }

    fun deleteSelectedPromos() {
        viewModelScope.launch {
            runCatching {
                deletePromoUseCase(_selectedIds.value)
            }.fold(
                onSuccess = {
                    loadPartnerPromos()
                    _selectedIds.value = emptyList()
                },
                onFailure = { loadPartnerPromos() }
            )
        }
    }

    init {
        loadPartnerPromos()
    }

    fun loadPartnerPromos() {
        viewModelScope.launch {
            _state.value = PromosState.Loading

            runCatching {
                getPartnerPromosUseCase()
            }.fold(
                onSuccess = { promosState ->
                    _state.value = promosState.getOrNull()
                        ?: PromosState.Error("Failed to load promos")
                },
                onFailure = { e ->
                    _state.value =
                        PromosState.Error(e.localizedMessage ?: "Unknown error")
                }
            )
        }
    }
}
