package ru.prodcontest.crazypeppers.feature_main_partner.domain.usecase

import android.content.SharedPreferences
import android.util.Log
import ru.prodcontest.crazypeppers.feature_main_partner.domain.repository.PartnerMainRepository
import ru.prodcontest.crazypeppers.feature_main_partner.domain.state.PromosState

class GetPartnerPromosUseCase(
    private val repository: PartnerMainRepository,
    private val sharedPrefs: SharedPreferences
) {
    suspend operator fun invoke(): Result<PromosState> = try {
        val partnerId = sharedPrefs.getString("partnerId", "")
            ?: throw IllegalStateException("Partner id is not set")
        val promos = repository.getPartnerPromos(partnerId)
        Log.d(TAG, "invoke: $promos")
        Result.success(PromosState.GetPartnerPromosSuccess(promos))
    } catch (e: Exception) {
        Log.e(TAG, "invoke: ", e)
        Result.failure(e)
    }

    companion object {
        const val TAG = "GetPartnerPromosUseCase"
    }
}
