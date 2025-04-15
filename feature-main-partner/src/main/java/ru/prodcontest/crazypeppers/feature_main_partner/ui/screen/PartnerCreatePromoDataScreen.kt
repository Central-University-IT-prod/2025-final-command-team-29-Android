@file:OptIn(ExperimentalMaterial3Api::class)

package ru.prodcontest.crazypeppers.feature_main_partner.ui.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import ru.prodcontest.crazypeppers.common.domain.Images
import ru.prodcontest.crazypeppers.feature_main_partner.R
import ru.prodcontest.crazypeppers.feature_main_partner.domain.model.PartnerPromoType
import ru.prodcontest.crazypeppers.feature_main_partner.ui.element.ImageOrPlaceholder
import ru.prodcontest.crazypeppers.feature_main_partner.ui.viewmodels.PartnerCreatePromoDataScreenViewModel

@Composable
fun PartnerCreatePromoDataScreen(
    viewModel: PartnerCreatePromoDataScreenViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {
    val promoData by viewModel.promoData.collectAsStateWithLifecycle()
    val showDatePickerDialog by viewModel.showDatePickerDialog.collectAsStateWithLifecycle()
    if (showDatePickerDialog) {
        DateRangePickerDialog(
            onDateRangeSelected = viewModel::onTimeLimitChanged,
            onDismiss = { viewModel.closeDatePickerDialog() }
        )
    }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        launch {
            viewModel.onSuccess.collect { result ->
                if (result) {
                    onBackPressed()
                }
            }
        }
        launch {
            viewModel.toastResId.collect { resId ->
                Toast.makeText(context, context.resources.getString(resId), Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
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
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            val imageId = Images.images[promoData.imageId]
            ImageOrPlaceholder(
                painter = if (imageId == null) null else painterResource(imageId),
                modifier = Modifier
                    .size(LocalConfiguration.current.screenWidthDp.dp / 2)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = promoData.title,
                onValueChange = viewModel::onTitleChanged,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = stringResource(id = R.string.title)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = promoData.description,
                onValueChange = viewModel::onDescriptionChanged,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = stringResource(id = R.string.description)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            DateLimits(viewModel)
            UsageLimit(viewModel)
            PromoTypeRadioGroup(viewModel)
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = promoData.condition,
                onValueChange = viewModel::onConditionChanged,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = stringResource(id = R.string.condition)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .safeDrawingPadding(),
                onClick = viewModel::publish
            ) {
                Text(text = stringResource(R.string.publish))
            }
        }
    }
}

@Composable
fun DateRangePickerDialog(
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateRangeSelected(
                        Pair(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis
                        )
                    )
                    onDismiss()
                }
            ) {
                Text(text = stringResource(android.R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(android.R.string.cancel))
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {
                Text(
                    text = stringResource(R.string.select_date_range)
                )
            },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp)
        )
    }
}

@Composable
fun DateLimits(
    viewModel: PartnerCreatePromoDataScreenViewModel
) {
    Column {
        val promoData by viewModel.promoData.collectAsStateWithLifecycle()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    if (promoData.timeLimitStart == null) {
                        viewModel.showDatePickerDialog()
                    } else {
                        viewModel.onTimeLimitChanged()
                    }
                }
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = promoData.timeLimitStart != null,
                onCheckedChange = { checked ->
                    if (checked) {
                        viewModel.showDatePickerDialog()
                    } else {
                        viewModel.onTimeLimitChanged()
                    }
                }
            )
            Text(
                text = stringResource(R.string.time_limit),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = if (promoData.timeLimitStart == null) 0.5f else 1f)
            )
        }
        AnimatedVisibility(
            visible = promoData.timeLimitStart != null
        ) {
            Card(
                onClick = viewModel::showDatePickerDialog
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = viewModel.getTimeLimitText() ?: ""
                    )
                }
            }
        }
    }
}

@Composable
fun UsageLimit(
    viewModel: PartnerCreatePromoDataScreenViewModel
) {
    val promoData by viewModel.promoData.collectAsStateWithLifecycle()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                if (promoData.usageLimit == null) {
                    viewModel.onUsageLimitChange("0")
                } else {
                    viewModel.onUsageLimitChange(null)
                }
            }
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = promoData.usageLimit != null,
            onCheckedChange = { checked ->
                if (checked) {
                    viewModel.onUsageLimitChange("0")
                } else {
                    viewModel.onUsageLimitChange(null)
                }
            }
        )
        Text(
            text = stringResource(R.string.total_usages),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = if (promoData.usageLimit == null) 0.5f else 1f)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Card {
            BasicTextField(
                enabled = promoData.usageLimit != null,
                value = promoData.usageLimit ?: "0",
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 15.dp)
                    .width(IntrinsicSize.Min)
                    .height(IntrinsicSize.Min),
                singleLine = true,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground.copy(alpha = if (promoData.usageLimit == null) 0.5f else 1f)),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = viewModel::onUsageLimitChange
            )
        }
    }
}

@Composable
fun PromoTypeRadioGroup(
    viewModel: PartnerCreatePromoDataScreenViewModel
) {
    val promoData by viewModel.promoData.collectAsStateWithLifecycle()
    val type = promoData.type
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    if (type !is PartnerPromoType.Bundle) {
                        viewModel.setPromoType(PartnerPromoType.Bundle(1))
                    }
                }
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = type is PartnerPromoType.Bundle,
                onClick = {
                    if (type !is PartnerPromoType.Bundle) {
                        viewModel.setPromoType(PartnerPromoType.Bundle(1))
                    }
                }
            )
            Text(
                text = stringResource(R.string.use_it_for_the),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = if (type !is PartnerPromoType.Bundle) 0.5f else 1f)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Card {
                BasicTextField(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 15.dp)
                        .width(IntrinsicSize.Min)
                        .height(IntrinsicSize.Min),
                    enabled = promoData.type is PartnerPromoType.Bundle,
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground.copy(alpha = if (type !is PartnerPromoType.Bundle) 0.5f else 1f)),
                    value = promoData.useOnTime ?: "1",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = viewModel::onUseOnTimeChanged
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = stringResource(R.string.time),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = if (type !is PartnerPromoType.Bundle) 0.5f else 1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    if (type != PartnerPromoType.Common) {
                        viewModel.setPromoType(PartnerPromoType.Common)
                    }
                }
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = type == PartnerPromoType.Common,
                onClick = {
                    if (type != PartnerPromoType.Common) {
                        viewModel.setPromoType(PartnerPromoType.Common)
                    }
                }
            )
            Text(
                text = stringResource(R.string.common),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = if (type != PartnerPromoType.Common) 0.5f else 1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    if (type != PartnerPromoType.Unique) {
                        viewModel.setPromoType(PartnerPromoType.Unique)
                    }
                }
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = type == PartnerPromoType.Unique,
                onClick = {
                    if (type != PartnerPromoType.Unique) {
                        viewModel.setPromoType(PartnerPromoType.Unique)
                    }
                }
            )
            Text(
                text = stringResource(R.string.unique),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = if (type != PartnerPromoType.Unique) 0.5f else 1f)
            )
        }
    }
}