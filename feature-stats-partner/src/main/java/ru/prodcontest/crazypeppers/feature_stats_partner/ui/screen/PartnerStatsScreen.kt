@file:OptIn(ExperimentalMaterial3Api::class)

package ru.prodcontest.crazypeppers.feature_stats_partner.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import ru.prodcontest.crazypeppers.feature_stats_partner.domain.stat.PartnerStatsState
import ru.prodcontest.crazypeppers.feature_stats_partner.ui.viewmodel.PartnerStatsViewModel
import ru.prodcontest.crazypeppers.feature_stats_partner.R

@Composable
fun PartnerStatsScreen(
    viewModel: PartnerStatsViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val stateValue = state
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
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
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (stateValue) {
                PartnerStatsState.Loading, PartnerStatsState.Idle -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is PartnerStatsState.Success -> {
                    val data = stateValue.data
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 10.dp,
                                    vertical = 5.dp
                                ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(fontSize = 40.sp)) {
                                            append(data.activeUsers.toString())
                                            append(" ")
                                        }
                                        withStyle(style = SpanStyle(fontSize = 14.sp)) {
                                            append(stringResource(R.string.active_users))
                                        }
                                    },
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(fontSize = 40.sp)) {
                                            append(data.dailyUsers.toString())
                                            append(" ")
                                        }
                                        withStyle(style = SpanStyle(fontSize = 14.sp)) {
                                            append(stringResource(R.string.daily_users))
                                        }
                                    },
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 10.dp,
                                    vertical = 5.dp
                                ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(fontSize = 40.sp)) {
                                            append("${data.conversion.toInt()}%")
                                            append(" ")
                                        }
                                        withStyle(style = SpanStyle(fontSize = 14.sp)) {
                                            append(stringResource(R.string.conversion))
                                        }
                                    },
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(fontSize = 40.sp)) {
                                            append(data.buyCount.toString())
                                            append(" ")
                                        }
                                        withStyle(style = SpanStyle(fontSize = 14.sp)) {
                                            append(stringResource(R.string.buy_count))
                                        }
                                    },
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 10.dp,
                                    vertical = 5.dp
                                ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(fontSize = 40.sp)) {
                                            append(data.dailyBuys.toString())
                                            append(" ")
                                        }
                                        withStyle(style = SpanStyle(fontSize = 14.sp)) {
                                            append(stringResource(R.string.daily_buys))
                                        }
                                    },
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(style = SpanStyle(fontSize = 40.sp)) {
                                            append(data.activePromos.toString())
                                            append(" ")
                                        }
                                        withStyle(style = SpanStyle(fontSize = 14.sp)) {
                                            append(stringResource(R.string.active_promos))
                                        }
                                    },
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                        Button(
                            onClick = {
                                viewModel.exportData(context)
                                Toast.makeText(context, R.string.saved, Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.export)
                            )
                        }
                    }
                }

                is PartnerStatsState.Error -> {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stateValue.message
                    )
                }
            }
        }
    }
}