package ru.prodcontest.crazypeppers.feature_stats_partner.domain.usecase

import android.content.SharedPreferences
import ru.prodcontest.crazypeppers.feature_stats_partner.domain.repository.PartnerStatRepository
import ru.prodcontest.crazypeppers.feature_stats_partner.domain.stat.PartnerStatsState

class GetPartnerStatUseCase(
    private val repository: PartnerStatRepository,
    private val sharedPrefs: SharedPreferences
) {
    suspend operator fun invoke(): Result<PartnerStatsState> = try {
        val partnerId = sharedPrefs.getString("partnerId", "")
            ?: throw IllegalStateException("Partner id is not set")
        val stats = repository.getStat(partnerId = partnerId)
        Result.success(PartnerStatsState.Success(stats))
    } catch (e: Exception) {
        Result.failure(e)
    }
}