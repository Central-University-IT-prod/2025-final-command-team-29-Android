package ru.prodcontest.crazypeppers.feature_main_partner.domain.usecase

import android.util.Log
import ru.prodcontest.crazypeppers.feature_main_partner.domain.repository.PartnerMainRepository
import ru.prodcontest.crazypeppers.feature_main_partner.domain.state.PromoDataState

data class GetPromoUseCase(private val repository: PartnerMainRepository) {
    suspend operator fun invoke(promoId: String): Result<PromoDataState> = try {
        val promo = repository.getPromo(promoId = promoId)
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
