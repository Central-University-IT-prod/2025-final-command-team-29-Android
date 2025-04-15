package ru.prodcontest.crazypeppers.tloyalty.di

import android.app.Application
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.KoinContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.prodcontest.crazypeppers.common.di.commonNetworkModule
import ru.prodcontest.crazypeppers.common.di.commonStorageModule
import ru.prodcontest.crazypeppers.feature_auth_customer.di.customerAuthNetworkModule
import ru.prodcontest.crazypeppers.feature_auth_customer.di.customerAuthRepositoryModule
import ru.prodcontest.crazypeppers.feature_auth_customer.di.customerAuthUseCaseModule
import ru.prodcontest.crazypeppers.feature_auth_customer.di.customerAuthViewModelModule
import ru.prodcontest.crazypeppers.feature_auth_partner.di.partnerAuthNetworkModule
import ru.prodcontest.crazypeppers.feature_auth_partner.di.partnerAuthRepositoryModule
import ru.prodcontest.crazypeppers.feature_auth_partner.di.partnerAuthUseCaseModule
import ru.prodcontest.crazypeppers.feature_auth_partner.di.partnerAuthViewModelModule
import ru.prodcontest.crazypeppers.feature_main_customer.di.customerMainNetworkModule
import ru.prodcontest.crazypeppers.feature_main_customer.di.customerMainRepositoryModule
import ru.prodcontest.crazypeppers.feature_main_customer.di.customerMainUseCaseModule
import ru.prodcontest.crazypeppers.feature_main_customer.di.customerMainViewModelModule
import ru.prodcontest.crazypeppers.feature_main_partner.di.partnerMainNetworkModule
import ru.prodcontest.crazypeppers.feature_main_partner.di.partnerMainRepositoryModule
import ru.prodcontest.crazypeppers.feature_main_partner.di.partnerMainUseCaseModule
import ru.prodcontest.crazypeppers.feature_main_partner.di.partnerMainViewModelModule
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.di.scanQrNetworkModule
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.di.scanQrRepositoryModule
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.di.scanQrUseCaseModule
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.di.scanQrViewModelModule
import ru.prodcontest.crazypeppers.feature_stats_partner.di.partnerStatNetworkModule
import ru.prodcontest.crazypeppers.feature_stats_partner.di.partnerStatRepositoryModule
import ru.prodcontest.crazypeppers.feature_stats_partner.di.partnerStatUseCaseModule
import ru.prodcontest.crazypeppers.feature_stats_partner.di.partnerStatViewModelModule
import ru.prodcontest.crazypeppers.tloyalty.ui.navigation.AppNavHost

val commonModules = listOf(
    commonNetworkModule,
    commonStorageModule
)

val scanQrModules = listOf(
    scanQrViewModelModule,
    scanQrUseCaseModule,
    scanQrNetworkModule,
    scanQrRepositoryModule,
)

val partnerMainModules = listOf(
    partnerMainViewModelModule,
    partnerMainNetworkModule,
    partnerMainRepositoryModule,
    partnerAuthUseCaseModule,
    partnerMainUseCaseModule
)

val customerMainModules = listOf(
    customerMainViewModelModule,
    customerMainNetworkModule,
    customerMainRepositoryModule,
    customerMainUseCaseModule,
    customerAuthUseCaseModule,
)

val customerAuthModules = listOf(
    customerAuthNetworkModule,
    customerAuthRepositoryModule,
    customerAuthUseCaseModule,
    customerAuthViewModelModule
)

val partnerAuthModules = listOf(
    partnerAuthNetworkModule,
    partnerAuthRepositoryModule,
    partnerAuthUseCaseModule,
    partnerAuthViewModelModule
)

val partnerStatModules = listOf(
    partnerStatNetworkModule,
    partnerStatRepositoryModule,
    partnerStatUseCaseModule,
    partnerStatViewModelModule
)

val appModules = commonModules + partnerAuthModules + customerAuthModules + partnerMainModules + customerMainModules + scanQrModules + partnerStatModules

class TLoyaltyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidContext(this@TLoyaltyApp)
            androidLogger(Level.DEBUG)
            modules(appModules)
        }
    }
}

@Composable
fun TLoyaltyApp(
    navController: NavHostController,
    sharedPrefs: SharedPreferences
) {
    KoinContext {
        Scaffold(
            contentWindowInsets = WindowInsets(bottom = 0.dp)
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AppNavHost(navController, sharedPrefs)
            }
        }
    }
}
