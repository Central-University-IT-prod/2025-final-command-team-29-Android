package ru.prodcontest.crazypeppers.feature_main_partner.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.prodcontest.crazypeppers.feature_main_partner.domain.state.PromoDataState
import ru.prodcontest.crazypeppers.feature_main_partner.domain.usecase.GetPromoUseCase
import java.text.SimpleDateFormat
import java.util.Locale

class PartnerPromoDataScreenViewModel(
    private val getPromoUseCase: GetPromoUseCase
): ViewModel() {
    private val _state = MutableStateFlow<PromoDataState>(PromoDataState.Idle)
    val state: StateFlow<PromoDataState> get() = _state

    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    fun getTimeLimitText(startTime: Long?, endTime: Long?): String? {
        if (startTime != null && endTime != null) {
            return "${dateFormatter.format(startTime)}—${dateFormatter.format(endTime)}"
        }
        return null
    }

    fun loadPromo(promoId: String) {
        viewModelScope.launch {
            _state.value = PromoDataState.Loading
            runCatching {
                getPromoUseCase(promoId = promoId)
            }.fold(
                onSuccess = { promoState ->
                    _state.value = promoState.getOrNull()
                        ?: PromoDataState.Error("Failed to load promos")
                },
                onFailure = { e ->
                    _state.value = PromoDataState.Error(e.localizedMessage ?: "Unknown error")
                }
            )
        }
    }
}