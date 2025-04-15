@file:OptIn(ExperimentalMaterial3Api::class)

package ru.prodcontest.crazypeppers.feature_main_partner.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import ru.prodcontest.crazypeppers.common.domain.Images
import ru.prodcontest.crazypeppers.feature_main_partner.R
import ru.prodcontest.crazypeppers.feature_main_partner.domain.model.PartnerPromoType
import ru.prodcontest.crazypeppers.feature_main_partner.domain.state.PromoDataState
import ru.prodcontest.crazypeppers.feature_main_partner.ui.element.ImageOrPlaceholder
import ru.prodcontest.crazypeppers.feature_main_partner.ui.viewmodels.PartnerPromoDataScreenViewModel

@Composable
fun PartnerPromoDataScreen(
    viewModel: PartnerPromoDataScreenViewModel = koinViewModel(),
    promoId: String,
    onBackPressed: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val stateValue = state
    LaunchedEffect(Unit) {
        viewModel.loadPromo(promoId = promoId)
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
                        val imageId = Images.images[data.imageId]
                        Spacer(modifier = Modifier.height(40.dp))
                        ImageOrPlaceholder(
                            painter = if (imageId == null) null else painterResource(imageId),
                            modifier = Modifier.size(
                                LocalConfiguration.current.screenWidthDp.dp / 3 * 2
                            )
                        )
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
                        val requiredToUse = if (data.type is PartnerPromoType.Bundle) "${stringResource(R.string.required_to_use)}: ${data.type.useOnTime}" else null
                        if (!requiredToUse.isNullOrEmpty()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = requiredToUse,
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