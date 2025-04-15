package ru.prodcontest.crazypeppers.tloyalty

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import org.koin.android.ext.android.inject
import ru.prodcontest.crazypeppers.tloyalty.di.TLoyaltyApp
import ru.prodcontest.crazypeppers.tloyalty.ui.theme.TLoyaltyTheme

class MainActivity : ComponentActivity() {

    private val sharedPrefs: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TLoyaltyTheme {
                val navController = rememberNavController()
                TLoyaltyApp(navController, sharedPrefs)
            }
        }
    }
}
