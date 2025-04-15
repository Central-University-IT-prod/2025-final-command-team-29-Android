@file:OptIn(ExperimentalMaterial3Api::class)

package ru.prodcontest.crazypeppers.feature_main_customer.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import ru.prodcontest.crazypeppers.feature_main_customer.ui.viewmodels.CustomerMainScreenViewModel
import ru.prodcontest.crazypeppers.feature_main_customer.R
import androidx.compose.runtime.getValue
import ru.prodcontest.crazypeppers.feature_main_customer.domain.state.PartnersState
import ru.prodcontest.crazypeppers.feature_main_customer.ui.elements.PartnerElement

@Composable
fun CustomerMainScreen(
    viewModel: CustomerMainScreenViewModel = koinViewModel(),
    onPartnerClick: (String, String) -> Unit,
    logout: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name)
                    )
                },
                actions = {
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
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (state) {
                PartnersState.Loading, PartnersState.Idle -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is PartnersState.Success -> {
                    LazyColumn {
                        items(
                            items = (state as PartnersState.Success).promos
                        ) { partnerData ->
                            PartnerElement(
                                partnerData = partnerData,
                                onClick = { onPartnerClick(partnerData.title, partnerData.id) }
                            )
                        }
                    }
                }
                is PartnersState.Error -> {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = (state as PartnersState.Error).message
                    )
                }
            }
        }
    }
}
