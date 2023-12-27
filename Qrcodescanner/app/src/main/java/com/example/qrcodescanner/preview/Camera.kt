package com.example.qrcodescanner.preview

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.qrcodescanner.R
import com.example.qrcodescanner.Screen
import com.example.qrcodescanner.shared.BarcodeAnalyser
import java.util.concurrent.Executors

@androidx.camera.core.ExperimentalGetImage
@Composable()
fun PreviewViewComposable(
    navController: NavHostController, onBardCodeScanner: (Any) -> Unit
) {
    val scannedBarcode = remember { mutableStateOf<String?>(null) }
    var scannedBarcodeList = remember { mutableListOf<String>("") }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(painter = painterResource(id = R.drawable.white_qrcode), contentDescription = "qr code" ,
            modifier = Modifier
                .width(400.dp).height(100.dp)
                .padding(top = 40.dp)
        )
    }
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
                                    scannedBarcodeList.add(it)
                                    onBardCodeScanner(scannedBarcodeList)
//                                    if(it.length === 1){
//                                        scannedBarcodeList.value = it
//                                        scannedBarcode.value = it
//                                        onBardCodeScanner(scannedBarcode.value
//                                        !!)
//                                    }
//                                    if(it.length > 1){
//                                        scannedBarcodeList.add = listOf(it)
//                                        onBardCodeScanner(scannedBarcodeList.value
//                                        )
//                                    }

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
}

