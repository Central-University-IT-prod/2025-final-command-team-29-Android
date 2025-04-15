@file:OptIn(ExperimentalMaterial3Api::class)

package ru.prodcontest.crazypeppers.feature_main_customer.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import ru.prodcontest.crazypeppers.feature_main_customer.domain.model.CustomerPromoType
import ru.prodcontest.crazypeppers.feature_main_customer.domain.state.PromoDataState
import ru.prodcontest.crazypeppers.feature_main_customer.ui.elements.QrCodeElement
import ru.prodcontest.crazypeppers.feature_main_customer.ui.viewmodels.CustomerPromoDataScreenViewModel

@Composable
fun CustomerPromoDataScreen(
    partnerId: String,
    promoId: String,
    viewModel: CustomerPromoDataScreenViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val stateValue = state
    LaunchedEffect(Unit) {
        viewModel.loadPromo(partnerId = partnerId, promoId = promoId)
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
                title = {}
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(10.dp),
        ) {
            when (stateValue) {
                PromoDataState.Loading, PromoDataState.Idle -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is PromoDataState.Success -> {
                    val data = stateValue.data
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(40.dp))
                        QrCodeElement(
                            data = viewModel.getQrData(promoId = promoId),
                            size = LocalConfiguration.current.screenWidthDp.dp / 3 * 2
                        )
                        if (data.type is CustomerPromoType.Bundle) {
                            Spacer(modifier = Modifier.height(5.dp))
                            Row(
                                modifier = Modifier.padding(horizontal = 20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                LinearProgressIndicator(
                                    modifier = Modifier.weight(1f),
                                    progress = { data.type.current.toFloat() / data.type.useOnTime.toFloat() }
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "${data.type.current}/${data.type.useOnTime}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = data.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = data.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        if (data.condition.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = data.condition,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        viewModel.getTimeLimitText(data.timeLimitStart, data.timeLimitEnd)?.let { timeText ->
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = timeText,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                is PromoDataState.Error -> {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = (state as PromoDataState.Error).message
                    )
                }
            }
        }
    }
}