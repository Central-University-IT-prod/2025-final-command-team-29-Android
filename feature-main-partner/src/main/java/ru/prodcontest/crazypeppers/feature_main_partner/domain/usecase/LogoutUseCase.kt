package ru.prodcontest.crazypeppers.feature_main_partner.domain.usecase

import android.content.SharedPreferences

class LogoutUseCase(
    private val sharedPrefs: SharedPreferences
) {
    operator fun invoke()  {
        val editor = sharedPrefs.edit()
        editor.remove("token")
        editor.remove("type")
        editor.remove("customerId")
        editor.apply()
    }
}