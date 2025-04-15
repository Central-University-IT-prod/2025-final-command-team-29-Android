package ru.prodcontest.crazypeppers.feature_main_customer.domain.usecase

import android.util.Log
import ru.prodcontest.crazypeppers.feature_main_customer.domain.repository.CustomerMainRepository
import ru.prodcontest.crazypeppers.feature_main_customer.domain.state.PromosState

class GetPartnerPromosUseCase(private val repository: CustomerMainRepository) {

    suspend operator fun invoke(partnerId: String): Result<PromosState> = try {
        val promos = repository.getPartnerPromos(partnerId)
        Log.d(TAG, "invoke: $promos")
        Result.success(PromosState.Success(promos))
    } catch (e: Exception) {
        Log.e(TAG, "invoke: ", e)
        Result.failure(e)
    }

    companion object {
        private const val TAG = "GetPartnerPromosUseCase"
    }
}
