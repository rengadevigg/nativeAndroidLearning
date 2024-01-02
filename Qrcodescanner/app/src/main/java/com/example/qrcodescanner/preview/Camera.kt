package com.example.qrcodescanner.preview

import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.qrcodescanner.shared.BarCodeAnalyser
import com.example.qrcodescanner.shared.ScannedInfo
import com.example.qrcodescanner.shared.parseLinkFromContent
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@androidx.camera.core.ExperimentalGetImage
@Composable()
fun PreviewViewComposable(
) {
    val viewModel = viewModel<CameraViewModel>();
    val scannedList by viewModel.scannedBarcodeList.collectAsState();
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var preview by remember { mutableStateOf<Preview?>(null) }
    val isScanningEnabled by viewModel.isScanningEnabled.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.padding(60.dp)

        ) {
            AndroidView(
                factory = { AndroidViewContext ->
                    PreviewView(AndroidViewContext).apply {
                        this.scaleType = PreviewView.ScaleType.FILL_CENTER
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                        )
                        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    }
                },

                update = { previewView ->
                        val cameraSelector: CameraSelector = CameraSelector.Builder()
                            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                            .build()
                        val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
                        val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
                            ProcessCameraProvider.getInstance(context)

                        cameraProviderFuture.addListener({
                            preview = Preview.Builder().build().also {
                                it.setSurfaceProvider(previewView.surfaceProvider)
                            }
                            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                            val barcodeAnalyser = BarCodeAnalyser { barcodes ->
                                barcodes.forEach { barcode ->
                                        barcode.rawValue?.let { barcode ->
                                            val url = parseLinkFromContent(barcode.toString())
                                            viewModel.updateListState(url)
                                             viewModel.updateScanningState(false)
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
                                if(isScanningEnabled){
                                    cameraProvider.bindToLifecycle(
                                        lifecycleOwner,
                                        cameraSelector,
                                        preview,
                                        imageAnalysis
                                    )
                                }

                            } catch (e: Exception) {
                                Log.d("TAG", "CameraPreview: ${e.localizedMessage}")
                            }
                        }, ContextCompat.getMainExecutor(context))
                    },

                modifier = Modifier
                    .height(500.dp)
                    .width(500.dp)
                    .padding(20.dp)
                )
        }
                Row {
                    if(!isScanningEnabled){
                    ScannedInfo(list = scannedList)
                    }
                }
    }
}


