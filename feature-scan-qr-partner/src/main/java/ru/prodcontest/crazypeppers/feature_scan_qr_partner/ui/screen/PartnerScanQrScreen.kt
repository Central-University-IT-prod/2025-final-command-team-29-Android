@file:OptIn(ExperimentalMaterial3Api::class)

package ru.prodcontest.crazypeppers.feature_scan_qr_partner.ui.screen

import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.common.util.concurrent.ListenableFuture
import org.koin.compose.viewmodel.koinViewModel
import ru.prodcontest.crazypeppers.common.domain.Images
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.R
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.model.BarCodeAnalyser
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.model.CustomerPromoType
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.domain.state.ScanQrState
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.ui.elements.GetCameraPermission
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.ui.elements.ImageOrPlaceholder
import ru.prodcontest.crazypeppers.feature_scan_qr_partner.ui.viewmodel.PartnerScanQrScreenViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
fun PartnerScanQrScreen(
    viewModel: PartnerScanQrScreenViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val stateValue = state
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
                .fillMaxSize()
        ) {
            when (stateValue) {
                ScanQrState.Processing -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                ScanQrState.Scanning -> {
                    GetCameraPermission(
                        whenGranted = {
                            CameraPreview(
                                onScanQr = { data ->
                                    if (data.split(";").size == 2) {
                                       viewModel.onScanSuccess(data)
                                    }
                                }
                            )
                        },
                        whenNotGranted = {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = stringResource(R.string.error)
                            )
                        }
                    )
                }

                is ScanQrState.Success -> {
                    val data = stateValue.promoData
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val imageId = Images.images[data.imageId]
                        Spacer(modifier = Modifier.height(40.dp))
                        ImageOrPlaceholder(
                            painter = if (imageId == null) null else painterResource(imageId),
                            modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp / 3 * 2)
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
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = data.condition,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            OutlinedButton(
                                onClick = {
                                    onBackPressed()
                                }
                            ) {
                                Text(text = stringResource(R.string.cancel))
                            }
                            Button(
                                onClick = {
                                    viewModel.activatePromo()
                                    onBackPressed()
                                }
                            ) {
                                Text(text = stringResource(R.string.apply))
                            }
                        }
                    }
                }

                is ScanQrState.Error -> {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = (state as ScanQrState.Error).message
                    )
                }
            }
        }
    }
}

@Composable
fun CameraPreview(
    onScanQr: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var preview by remember { mutableStateOf<Preview?>(null) }

    AndroidView(
        factory = { context ->
            PreviewView(context).apply {
                this.scaleType = PreviewView.ScaleType.FILL_CENTER
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        update = { previewView ->
            val cameraSelector: CameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()
            val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
            val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
                ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener({
                preview = Preview.Builder().build().also {
                    it.surfaceProvider = previewView.surfaceProvider
                }
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                val barcodeAnalyser = BarCodeAnalyser { barcodes ->
                    barcodes.forEach { barcode ->
                        barcode.rawValue?.let { barcodeValue ->
                            onScanQr(barcodeValue)
                        }
                    }
                }
                val imageAnalysis: ImageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor, barcodeAnalyser)
                    }

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                } catch (_: Exception) {
                }
            }, ContextCompat.getMainExecutor(context))
        }
    )
}