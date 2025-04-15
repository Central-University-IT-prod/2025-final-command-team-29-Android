package ru.prodcontest.crazypeppers.feature_stats_partner.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.prodcontest.crazypeppers.feature_stats_partner.domain.stat.PartnerStatsState
import ru.prodcontest.crazypeppers.feature_stats_partner.domain.usecase.GetPartnerStatFileUseCase
import ru.prodcontest.crazypeppers.feature_stats_partner.domain.usecase.GetPartnerStatUseCase

class PartnerStatsViewModel(
    private val getPartnerStatUseCase: GetPartnerStatUseCase,
    private val getPartnerStatFileUseCase: GetPartnerStatFileUseCase,
): ViewModel() {
    private val _state = MutableStateFlow<PartnerStatsState>(PartnerStatsState.Idle)
    val state: StateFlow<PartnerStatsState> get() = _state

    init {
        loadStats()
    }

    private fun loadStats() {
        viewModelScope.launch {
            _state.value = PartnerStatsState.Loading

            runCatching {
                withContext(Dispatchers.IO) { getPartnerStatUseCase() }
            }.fold(
                onSuccess = { stats ->
                    _state.value = stats.getOrNull()
                        ?: PartnerStatsState.Error("Failed to load partners")
                },
                onFailure = { e ->
                    _state.value =
                        PartnerStatsState.Error(e.localizedMessage ?: "Unknown error")
                }
            )
        }
    }

    fun exportData(context: Context) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) { getPartnerStatFileUseCase(context) }
            }.fold(
                onSuccess = {},
                onFailure = {}
            )
        }
    }
}