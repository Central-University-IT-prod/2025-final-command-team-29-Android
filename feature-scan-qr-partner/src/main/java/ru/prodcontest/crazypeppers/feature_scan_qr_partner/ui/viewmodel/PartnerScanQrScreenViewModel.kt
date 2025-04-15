package ru.prodcontest.crazypeppers.feature_scan_qr_partner.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.state.ScanQrState
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.usecase.ActivatePromoUseCase
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.usecase.GetPromoUseCase

class PartnerScanQrScreenViewModel(
    private val getPromoUseCase: GetPromoUseCase,
    private val activatePromoUseCase: ActivatePromoUseCase
): ViewModel() {
    private val _state = MutableStateFlow<ScanQrState>(ScanQrState.Scanning)
    val state: StateFlow<ScanQrState> get() = _state

    private var customerId: String = ""
    private var promoId: String = ""

    fun onScanSuccess(data: String) {
        viewModelScope.launch {
            _state.value = ScanQrState.Processing
            customerId = data.split(";")[0]
            promoId = data.split(";")[1]
            runCatching {
                getPromoUseCase(customerId = customerId, promoId = promoId)
            }.fold(
                onSuccess = { promoState ->
                    _state.value = promoState.getOrNull()
                        ?: ScanQrState.Error("Failed to load promos")
                },
                onFailure = { e ->
                    _state.value = ScanQrState.Error(e.localizedMessage ?: "Unknown error")
                }
            )
        }
    }

    fun activatePromo() {
        viewModelScope.launch {
            runCatching {
                activatePromoUseCase(customerId = customerId, promoId = promoId)
            }.fold(
                onSuccess = { },
                onFailure = { }
            )
        }
    }
}