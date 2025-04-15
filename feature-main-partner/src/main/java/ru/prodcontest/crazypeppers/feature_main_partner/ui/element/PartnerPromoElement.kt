package ru.prodcontest.crazypeppers.feature_main_partner.ui.element

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PartnerPromoElement(
    title: String,
    timeLimit: String?,
    imageId: Int?,
    useOnTimeText: String?,
    selected: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 5.dp,
                horizontal = 10.dp
            )
            .clip(RoundedCornerShape(16.dp))
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = RoundedCornerShape(16.dp),
        border = if (selected) { BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onSurface) } else null
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            ImageOrPlaceholder(
                painter = if (imageId == null) null else painterResource(imageId),
                modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp / 3)
            )
            Spacer(modifier = Modifier.width(10.dp))
            if (useOnTimeText == null) {
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
                    Text(
                        text = useOnTimeText,
                        style = MaterialTheme.typography.bodyMedium
                    )
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