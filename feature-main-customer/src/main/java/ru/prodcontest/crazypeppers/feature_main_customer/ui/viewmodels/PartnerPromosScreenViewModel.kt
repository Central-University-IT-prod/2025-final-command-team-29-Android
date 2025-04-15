package ru.prodcontest.crazypeppers.feature_main_customer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.prodcontest.crazypeppers.feature_main_customer.domain.state.PromosState
import ru.prodcontest.crazypeppers.feature_main_customer.domain.usecase.GetPartnerPromosUseCase
import java.text.SimpleDateFormat
import java.util.Locale

class PartnerPromosScreenViewModel(
    private val getPartnerPromosUseCase: GetPartnerPromosUseCase
) : ViewModel() {
    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    private val _state = MutableStateFlow<PromosState>(PromosState.Idle)
    val state: StateFlow<PromosState> get() = _state

    fun getTimeLimitText(startTime: Long?, endTime: Long?): String? {
        if (startTime != null && endTime != null) {
            return "${dateFormatter.format(startTime)}—${dateFormatter.format(endTime)}"
        }
        return null
    }

    fun loadData(partnerId: String) {
        viewModelScope.launch {
            _state.value = PromosState.Loading

            runCatching {
                getPartnerPromosUseCase(partnerId)
            }.fold(
                onSuccess = { promosState ->
                    _state.value = promosState.getOrNull()
                        ?: PromosState.Error("Failed to load promos")
                },
                onFailure = { e ->
                    _state.value = PromosState.Error(e.localizedMessage ?: "Unknown error")
                }
            )
        }
    }
}
