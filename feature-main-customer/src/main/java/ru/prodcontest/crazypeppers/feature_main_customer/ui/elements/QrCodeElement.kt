package ru.prodcontest.crazypeppers.feature_main_customer.ui.elements

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun QrCodeElement(
    data: String,
    modifier: Modifier = Modifier,
    size: Dp = 150.dp,
    padding: Dp = 0.dp
) {
    val density = LocalDensity.current
    val sizePx = with(density) { size.roundToPx() }
    val paddingPx = with(density) { padding.roundToPx() }

    var qrBitmap by remember(data) {
        mutableStateOf<Bitmap?>(null)
    }

    LaunchedEffect(qrBitmap) {
        if (qrBitmap != null) return@LaunchedEffect

        launch(Dispatchers.IO) {
            try {
                val writer = QRCodeWriter()
                val bitMatrix = writer.encode(
                    data,
                    BarcodeFormat.QR_CODE,
                    sizePx,
                    sizePx,
                    mapOf(EncodeHintType.MARGIN to paddingPx)
                )

                val bitmap = Bitmap.createBitmap(
                    bitMatrix.width,
                    bitMatrix.height,
                    Bitmap.Config.ARGB_8888
                )

                for (x in 0 until bitMatrix.width) {
                    for (y in 0 until bitMatrix.height) {
                        bitmap.setPixel(
                            x,
                            y,
                            if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
                        )
                    }
                }

                qrBitmap = bitmap
            } catch (_: Exception) {

            }
        }
    }

    Box(modifier = modifier) {
        qrBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "QR code",
                modifier = Modifier.size(size)
            )
        }
    }
}