package ru.prodcontest.crazypeppers.tloyalty.ui.navigation

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import ru.prodcontest.crazypeppers.feature_auth_customer.ui.screen.CustomerWelcomeScreen
import ru.prodcontest.crazypeppers.feature_auth_partner.ui.screen.PartnerWelcomeScreen
import ru.prodcontest.crazypeppers.feature_main_customer.ui.screen.CustomerMainScreen
import ru.prodcontest.crazypeppers.feature_main_customer.ui.screen.CustomerPromoDataScreen
import ru.prodcontest.crazypeppers.feature_main_customer.ui.screen.PartnerPromosScreen
import ru.prodcontest.crazypeppers.feature_main_partner.ui.screen.PartnerMainScreen
import ru.prodcontest.crazypeppers.feature_main_partner.ui.screen.PartnerCreatePromoDataScreen
import ru.prodcontest.crazypeppers.feature_main_partner.ui.screen.PartnerPromoDataScreen
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.ui.screen.PartnerScanQrScreen
import ru.prodcontest.crazypeppers.feature_stats_partner.ui.screen.PartnerStatsScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    sharedPrefs: SharedPreferences
) {
    val startDestination = remember {
        when (sharedPrefs.getString("type", null)) {
            "customer" -> AppGraph.Customer
            "partner" -> AppGraph.Partner
            else -> AppGraph.Customer
        }
    }

    LaunchedEffect(Unit) {
        val token = sharedPrefs.getString("token", null)
        val userType = sharedPrefs.getString("type", null)

        Log.d("AppNavHost", "token: $token, userType: $userType")

        if (token != null) {
            when (userType) {
                "customer" -> navHostController.navigate(CustomerGraph.Main) {
                    popUpTo(AppGraph.Customer) { inclusive = true }
                    launchSingleTop = true
                }

                "partner" -> navHostController.navigate(PartnerGraph.Main) {
                    popUpTo(AppGraph.Partner) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }
    val startDestinationCustomer = remember {
        when (sharedPrefs.getString("type", null)) {
            "customer" -> CustomerGraph.Main
            else -> CustomerGraph.Welcome
        }
    }
    val startDestinationPartner = remember {
        when (sharedPrefs.getString("type", null)) {
            "partner" -> PartnerGraph.Main
            else -> PartnerGraph.Welcome
        }
    }
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }
    ) {
        navigation<AppGraph.Customer>(startDestination = startDestinationCustomer) {
            composable<CustomerGraph.Welcome> {
                CustomerWelcomeScreen(
                    onPartnerClick = {
                        navHostController.navigate(AppGraph.Partner) {
                            popUpTo(AppGraph.Customer) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onSignInSuccess = {
                        navHostController.navigate(CustomerGraph.Main) {
                            popUpTo(AppGraph.Customer) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onSignUpSuccess = {
                        navHostController.navigate(CustomerGraph.Main) {
                            popUpTo(AppGraph.Customer) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<CustomerGraph.Main> {
                CustomerMainScreen(
                    onPartnerClick = { partnerTitle, partnerId ->
                        navHostController.navigate(
                            CustomerGraph.PartnerPromos(
                                partnerTitle,
                                partnerId
                            )
                        ) {
                            launchSingleTop = true
                        }
                    },
                    logout = {
                        navHostController.navigate(CustomerGraph.Welcome) {
                            popUpTo(CustomerGraph.Welcome) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<CustomerGraph.PartnerPromos> { navBackStackEntry ->
                val data = navBackStackEntry.toRoute<CustomerGraph.PartnerPromos>()
                PartnerPromosScreen(
                    partnerId = data.partnerId,
                    partnerTitle = data.partnerTitle,
                    onPromoClick = { partnerId, promoId ->
                        navHostController.navigate(CustomerGraph.PromoData(partnerId = partnerId, promoId = promoId)) {
                            launchSingleTop = true
                        }
                    },
                    onBackPressed = {
                        navHostController.popBackStack()
                    }
                )
            }
            composable<CustomerGraph.PromoData> { navBackStackEntry ->
                val data = navBackStackEntry.toRoute<CustomerGraph.PromoData>()
                CustomerPromoDataScreen(
                    partnerId = data.partnerId,
                    promoId = data.promoId,
                    onBackPressed = {
                        navHostController.popBackStack()
                    }
                )
            }
        }
        navigation<AppGraph.Partner>(startDestination = startDestinationPartner) {
            composable<PartnerGraph.Welcome> {
                PartnerWelcomeScreen(
                    onCustomerClick = {
                        navHostController.navigate(AppGraph.Customer) {
                            popUpTo(AppGraph.Partner) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onSignInSuccess = {
                        navHostController.navigate(PartnerGraph.Main) {
                            popUpTo(AppGraph.Partner) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onSignUpSuccess = {
                        navHostController.navigate(PartnerGraph.Main) {
                            popUpTo(AppGraph.Partner) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<PartnerGraph.Main> {
                PartnerMainScreen(
                    onStatsClick = {
                        navHostController.navigate(PartnerGraph.Stats) {
                            launchSingleTop = true
                        }
                    },
                    onQrClick = {
                        navHostController.navigate(PartnerGraph.ScanQr) {
                            launchSingleTop = true
                        }
                    },
                    onPromoClick = { promoId ->
                        navHostController.navigate(PartnerGraph.PromoData(promoId)) {
                            launchSingleTop = true
                        }
                    },
                    createNewPromo = {
                        navHostController.navigate(PartnerGraph.CreatePromoData) {
                            launchSingleTop = true
                        }
                    },
                    logout = {
                        navHostController.navigate(PartnerGraph.Welcome) {
                            popUpTo(PartnerGraph.Welcome) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<PartnerGraph.PromoData> { navBackStackEntry ->
                val data = navBackStackEntry.toRoute<PartnerGraph.PromoData>()
                PartnerPromoDataScreen(
                    promoId = data.promoId,
                    onBackPressed = {
                        navHostController.popBackStack()
                    }
                )
            }
            composable<PartnerGraph.CreatePromoData> {
                PartnerCreatePromoDataScreen(
                    onBackPressed = {
                        navHostController.popBackStack()
                    }
                )
            }
            composable<PartnerGraph.ScanQr> {
                PartnerScanQrScreen(
                    onBackPressed = {
                        navHostController.popBackStack()
                    }
                )
            }
            composable<PartnerGraph.Stats> {
                PartnerStatsScreen(
                    onBackPressed = {
                        navHostController.popBackStack()
                    }
                )
            }
        }
    }
}
