package ru.prodcontest.crazypeppers.feature_main_customer.ui.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun CustomerPromoElement(
    title: String,
    timeLimit: String?,
    usedCountText: String?,
    usedCountPercent: Float?,
    painter: Painter?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 5.dp,
                horizontal = 10.dp
            ),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            ImageOrPlaceholder(
                painter = painter,
                modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp / 3)
            )
            Spacer(modifier = Modifier.width(10.dp))
            if (usedCountText == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(LocalConfiguration.current.screenWidthDp.dp / 3)
                ) {
                    Text(
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        text = title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    timeLimit?.let {
                        Text(
                            modifier = Modifier.align(Alignment.BottomEnd),
                            text = timeLimit,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(LocalConfiguration.current.screenWidthDp.dp / 3)
                ) {
                    Text(
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        text = title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LinearProgressIndicator(
                            modifier = Modifier.weight(1f),
                            progress = { usedCountPercent ?: 0f }
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = usedCountText,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    timeLimit?.let {
                        Text(
                            modifier = Modifier.align(Alignment.End),
                            text = timeLimit,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}