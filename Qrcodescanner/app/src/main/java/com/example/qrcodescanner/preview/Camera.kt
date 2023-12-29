package com.example.qrcodescanner.preview

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.qrcodescanner.Screen
import com.example.qrcodescanner.shared.BarcodeAnalyser
import com.example.qrcodescanner.shared.ScannedInfo
import java.util.concurrent.Executors

@androidx.camera.core.ExperimentalGetImage
@Composable()
fun PreviewViewComposable(
    navController: NavHostController, onBardCodeScanner: (Any) -> Unit
) {
    val viewModel = CameraViewModel()
    val scannedList by viewModel.scannedBarcodeList.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row (
            modifier = Modifier.padding(60.dp)

        ){

            AndroidView(
                { context ->
                    val cameraExecutor = Executors.newSingleThreadExecutor()
                    val previewView = PreviewView(context).also {
                        it.scaleType = PreviewView.ScaleType.FILL_CENTER
                    }
                    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                    cameraProviderFuture.addListener({
                        val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                        val preview = Preview.Builder()
                            .build()
                            .also {
                                it.setSurfaceProvider(previewView.surfaceProvider)
                            }

                        val imageCapture = ImageCapture.Builder().build()

                        val imageAnalyzer = ImageAnalysis.Builder()
                            .build()
                            .also { it ->
                                it.setAnalyzer(cameraExecutor, BarcodeAnalyser(context = context){
                                    viewModel._scannedBarcodeList.value = listOf(it)
                                    onBardCodeScanner(scannedList)
                                    navController.navigate(Screen.ScannedInfo.route)
                                })
                            }

                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                        try {
                            // Unbind use cases before rebinding
                            cameraProvider.unbindAll()

                            // Bind use cases to camera
                            cameraProvider.bindToLifecycle(
                                context as ComponentActivity, cameraSelector, preview, imageCapture, imageAnalyzer)

                        } catch(exc: Exception) {
                            Log.e("DEBUG", "Use case binding failed", exc)
                        }
                    }, ContextCompat.getMainExecutor(context))
                    previewView
                },

                modifier = Modifier
                    .width(550.dp)
                    .height(550.dp)
                    .padding(top = 80.dp, bottom = 80.dp)
            ) {

            }
        }
    }

    Column {
        if(scannedList.isNotEmpty()){
            ScannedInfo(list = scannedList)
        }
    }
}

