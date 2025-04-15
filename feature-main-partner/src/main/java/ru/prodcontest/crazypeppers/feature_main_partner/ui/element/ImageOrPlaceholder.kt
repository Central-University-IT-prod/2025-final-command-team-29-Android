package ru.prodcontest.crazypeppers.feature_main_partner.ui.element

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
@Composable
fun ImageOrPlaceholder(
    painter: Painter? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        if (painter == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(60.dp),
                    imageVector = Icons.Default.Image,
                    contentDescription = "Placeholder"
                )
            }
        } else {
            Image(
                painter = painter,
                contentScale = ContentScale.Fit,
                contentDescription = "Image"
            )
        }
    }
}