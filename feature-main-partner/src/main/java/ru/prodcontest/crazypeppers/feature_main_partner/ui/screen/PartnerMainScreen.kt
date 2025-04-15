@file:OptIn(ExperimentalMaterial3Api::class)

package ru.prodcontest.crazypeppers.feature_main_partner.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import ru.prodcontest.crazypeppers.feature_main_partner.ui.viewmodels.PartnerMainScreenViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import ru.prodcontest.crazypeppers.common.domain.Images
import ru.prodcontest.crazypeppers.feature_main_partner.R
import ru.prodcontest.crazypeppers.feature_main_partner.domain.model.PartnerPromoType
import ru.prodcontest.crazypeppers.feature_main_partner.domain.state.PromosState
import ru.prodcontest.crazypeppers.feature_main_partner.ui.element.PartnerPromoElement

@Composable
fun PartnerMainScreen(
    viewModel: PartnerMainScreenViewModel = koinViewModel(),
    onStatsClick: () -> Unit,
    onQrClick: () -> Unit,
    onPromoClick: (String) -> Unit,
    createNewPromo: () -> Unit,
    logout: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val stateValue = state
    val selectedIds by viewModel.selectedIds.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.loadPartnerPromos()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name)
                    )
                },
                actions = {
                    if (selectedIds.isNotEmpty()) {
                        IconButton(
                            onClick = viewModel::deleteSelectedPromos
                        ) {
                            Icon(
                                imageVector = Icons.Default.DeleteOutline,
                                contentDescription = "Delete"
                            )
                        }
                    } else {
                        TextButton(
                            onClick = {
                                viewModel.logout()
                                logout()
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.logout)
                            )
                        }
                        IconButton(
                            onClick = onQrClick
                        ) {
                            Icon(
                                imageVector = Icons.Default.QrCodeScanner,
                                contentDescription = "QrCodeScanner"
                            )
                        }
                        IconButton(
                            onClick = onStatsClick
                        ) {
                            Icon(
                                imageVector = Icons.Default.DataUsage,
                                contentDescription = "Stats"
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = createNewPromo
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (stateValue) {
                is PromosState.GetPartnerPromosSuccess -> {
                    LazyColumn {
                        items(
                            items = stateValue.promos
                        ) { promoData ->
                            val imageId = Images.images[promoData.imageId]
                            PartnerPromoElement(
                                title = promoData.title,
                                timeLimit = viewModel.getTimeLimitText(promoData.timeLimitStart, promoData.timeLimitEnd),
                                useOnTimeText = if (promoData.type is PartnerPromoType.Bundle) "${stringResource(R.string.required_to_use)}: ${promoData.type.useOnTime}" else null,
                                selected = promoData.id in selectedIds,
                                imageId = imageId,
                                onClick = {
                                    if (selectedIds.isEmpty()) {
                                        onPromoClick(promoData.id)
                                    } else {
                                        viewModel.changePromoSelected(promoData.id)
                                    }
                                },
                                onLongClick = {
                                    viewModel.changePromoSelected(promoData.id)
                                }
                            )
                        }
                    }
                }
                is PromosState.Error -> {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stateValue.message
                    )
                }
                else -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}