package ru.prodcontest.crazypeppers.feature_main_partner.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.prodcontest.crazypeppers.feature_main_partner.R
import ru.prodcontest.crazypeppers.feature_main_partner.domain.model.ChangedPromoData
import ru.prodcontest.crazypeppers.feature_main_partner.domain.model.PartnerPromoData
import ru.prodcontest.crazypeppers.feature_main_partner.domain.model.PartnerPromoType
import ru.prodcontest.crazypeppers.feature_main_partner.domain.usecase.CreatePromoUseCase
import java.text.SimpleDateFormat
import java.util.Locale

class PartnerCreatePromoDataScreenViewModel(
    private val createPromoUseCase: CreatePromoUseCase
) : ViewModel() {
    private val _promoData = MutableStateFlow(ChangedPromoData.empty((0..4).random()))
    val promoData: StateFlow<ChangedPromoData> get() = _promoData

    private val _onSuccess = MutableSharedFlow<Boolean>()
    val onSuccess: SharedFlow<Boolean> get() = _onSuccess

    private val _toastResId = MutableSharedFlow<Int>()
    val toastResId: SharedFlow<Int> get() = _toastResId

    private val _showDatePickerDialog = MutableStateFlow(false)
    val showDatePickerDialog: StateFlow<Boolean> get() = _showDatePickerDialog

    private val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    fun onTitleChanged(title: String) {
        _promoData.value = _promoData.value.copy(title = title)
    }

    fun onDescriptionChanged(description: String) {
        _promoData.value = _promoData.value.copy(description = description)
    }

    fun onConditionChanged(condition: String) {
        _promoData.value = _promoData.value.copy(condition = condition)
    }

    fun onTimeLimitChanged(data: Pair<Long?, Long?>) {
        if (data.first != null && data.second != null)
            _promoData.value =
                _promoData.value.copy(timeLimitStart = data.first, timeLimitEnd = data.second)
    }

    fun onTimeLimitChanged() {
        _promoData.value = _promoData.value.copy(timeLimitStart = null, timeLimitEnd = null)
    }

    fun getTimeLimitText(): String? {
        val startTime = promoData.value.timeLimitStart
        val endTime = promoData.value.timeLimitEnd
        if (startTime != null && endTime != null) {
            return "${dateFormatter.format(startTime)}—${dateFormatter.format(endTime)}"
        }
        return null
    }

    fun showDatePickerDialog() {
        _showDatePickerDialog.value = true
    }

    fun closeDatePickerDialog() {
        _showDatePickerDialog.value = false
    }

    fun onUsageLimitChange(usageLimit: String?) {
        if ((usageLimit?.length ?: 0) <= Int.MAX_VALUE.toString().length - 1) {
            _promoData.value = _promoData.value.copy(usageLimit = usageLimit)
        }
    }

    fun onUseOnTimeChanged(useOnTime: String?) {
        if ((useOnTime?.length ?: 0) <= 4) {
            _promoData.value = _promoData.value.copy(useOnTime = useOnTime)
        }
    }

    fun setPromoType(promoType: PartnerPromoType) {
        _promoData.value = _promoData.value.copy(type = promoType)
    }

    fun publish() {
        val title = _promoData.value.title
        val description = _promoData.value.description
        val timeLimitStart = _promoData.value.timeLimitStart
        val timeLimitEnd = _promoData.value.timeLimitEnd
        val usageLimit = _promoData.value.usageLimit
        val type = _promoData.value.type // Hardcode

        if (title.isEmpty()) {
            errorToast(R.string.title_is_empty)
        } else if (description.isEmpty()) {
            errorToast(R.string.description_is_empty)
        } else if (listOf(timeLimitStart, timeLimitEnd).count { it == null } == 1) {
            errorToast(R.string.incorrect_date)
        } else if (usageLimit != null && (usageLimit.toIntOrNull() ?: -1) <= 0) {
            errorToast(R.string.incorrect_usage_limit)
        } else if (type is PartnerPromoType.Bundle && (_promoData.value.useOnTime?.toIntOrNull() ?: -1) <= 1) {
            errorToast(R.string.incorrect_use_on_time)
        } else {
            createPromo(
                PartnerPromoData(
                    id = "",
                    title = title,
                    description = description,
                    timeLimitStart = timeLimitStart,
                    timeLimitEnd = timeLimitEnd,
                    usageLimit = usageLimit?.toIntOrNull() ?: 1,
                    condition = _promoData.value.condition,
                    type = if (type is PartnerPromoType.Bundle) {
                        type.copy(
                            useOnTime = _promoData.value.useOnTime?.toIntOrNull() ?: 2
                        )
                    } else type,
                    imageId = promoData.value.imageId
                )
            )
        }
    }

    fun createPromo(promoData: PartnerPromoData) {
        viewModelScope.launch {
            runCatching {
                createPromoUseCase(promoData)
            }.fold(
                onSuccess = {
                    successToast()
                    _onSuccess.emit(true)
                },
                onFailure = { e ->
                    e.printStackTrace()
                }
            )
        }
    }

    fun errorToast(resId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _toastResId.emit(resId)
        }
    }

    fun successToast() {
        viewModelScope.launch(Dispatchers.IO) {
            _toastResId.emit(R.string.success)
        }
    }
}
