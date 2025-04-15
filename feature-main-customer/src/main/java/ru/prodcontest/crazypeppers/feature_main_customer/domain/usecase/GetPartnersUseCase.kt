package ru.prodcontest.crazypeppers.feature_main_customer.domain.usecase

import android.util.Log
import ru.prodcontest.crazypeppers.feature_main_customer.domain.repository.CustomerMainRepository
import ru.prodcontest.crazypeppers.feature_main_customer.domain.state.PartnersState

class GetPartnersUseCase(private val repository: CustomerMainRepository) {

    suspend operator fun invoke(): Result<PartnersState> = try {
        val partners = repository.getPartners()
        Log.d(TAG, "invoke: $partners")
        Result.success(PartnersState.Success(partners))
    } catch (e: Exception) {
        Log.e(TAG, "invoke: ", e)
        Result.failure(e)
    }

    companion object {
        private const val TAG = "GetPartnersUseCase"
    }
}
