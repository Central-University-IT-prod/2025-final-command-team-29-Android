package ru.prodcontest.crazypeppers.feature_main_customer.domain.usecase

import android.util.Log
import ru.prodcontest.crazypeppers.feature_main_customer.domain.repository.CustomerMainRepository
import ru.prodcontest.crazypeppers.feature_main_customer.domain.state.PromoDataState

class GetPromoUseCase(private val repository: CustomerMainRepository) {

    suspend operator fun invoke(partnerId: String, promoId: String): Result<PromoDataState> = try {
        val promo = repository.getPromo(promoId = promoId, partnerId = partnerId)
        Log.d(TAG, "invoke: $promo")
        Result.success(PromoDataState.Success(promo))
    } catch (e: Exception) {
        Log.e(TAG, "invoke: ", e)
        Result.failure(e)
    }

    companion object {
        private const val TAG = "GetPartnerPromosUseCase"
    }
}
