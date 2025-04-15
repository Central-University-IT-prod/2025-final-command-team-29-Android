@file:OptIn(ExperimentalMaterial3Api::class)

package ru.prodcontest.crazypeppers.feature_main_customer.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import ru.prodcontest.crazypeppers.common.domain.Images
import ru.prodcontest.crazypeppers.feature_main_customer.domain.model.CustomerPromoType
import ru.prodcontest.crazypeppers.feature_main_customer.domain.state.PromosState
import ru.prodcontest.crazypeppers.feature_main_customer.ui.elements.CustomerPromoElement
import ru.prodcontest.crazypeppers.feature_main_customer.ui.viewmodels.PartnerPromosScreenViewModel

@Composable
fun PartnerPromosScreen(
    viewModel: PartnerPromosScreenViewModel = koinViewModel(),
    partnerId: String,
    partnerTitle: String,
    onPromoClick: (partnerId: String, promoId: String) -> Unit,
    onBackPressed: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val stateValue = state
    LaunchedEffect(Unit) {
        viewModel.loadData(partnerId)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onBackPressed
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text(
                        text = partnerTitle
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (stateValue) {
                PromosState.Loading, PromosState.Idle -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is PromosState.Success -> {
                    LazyColumn {
                        items(
                            items = stateValue.promos
                        ) { promoData ->
                            val imageId = Images.images[promoData.imageId]
                            CustomerPromoElement(
                                title = promoData.title,
                                painter = if (imageId == null) null else painterResource(imageId),
                                timeLimit = viewModel.getTimeLimitText(
                                    promoData.timeLimitStart,
                                    promoData.timeLimitEnd
                                ),
                                usedCountText = if (promoData.type is CustomerPromoType.Bundle) "${promoData.type.current}/${promoData.type.useOnTime}" else null,
                                usedCountPercent = if (promoData.type is CustomerPromoType.Bundle) promoData.type.current.toFloat() / promoData.type.useOnTime.toFloat() else null,
                                onClick = {
                                    onPromoClick(partnerId, promoData.id)
                                }
                            )
                        }
                    }
                }

                is PromosState.Error -> {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = (state as PromosState.Error).message
                    )
                }
            }
        }
    }
}