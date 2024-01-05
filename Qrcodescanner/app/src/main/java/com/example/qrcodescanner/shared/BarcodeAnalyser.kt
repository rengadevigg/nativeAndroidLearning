package com.example.qrcodescanner.shared

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.TimeUnit


@SuppressLint("UnsafeOptInUsageError")
class BarCodeAnalyser(
    private val onBarcodeDetected: (barcodes: List<Barcode>) -> Unit,
) : ImageAnalysis.Analyzer {
    private var lastAnalyzedTimeStamp = 0L

    override fun analyze(image: ImageProxy) {
        val currentTimestamp = System.currentTimeMillis()
        if (currentTimestamp - lastAnalyzedTimeStamp >= TimeUnit.SECONDS.toMillis(1)) {
            image.image?.let { imageToAnalyze ->
                val options = BarcodeScannerOptions.Builder()
                    .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                    .build()
                val barcodeScanner = BarcodeScanning.getClient(options)
                val imageToProcess =
                    InputImage.fromMediaImage(imageToAnalyze, image.imageInfo.rotationDegrees)
                barcodeScanner.process(imageToProcess)
                    .addOnSuccessListener { barcodes ->
                        if (barcodes.isNotEmpty()) {
                            onBarcodeDetected(barcodes)
                        } else {
                            Log.d("TAG", "analyze: No barcode Scanned")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("TAG", "BarcodeAnalyser: Something went wrong $exception")
                    }
                    .addOnCompleteListener {
                        image.close()
                    }
            }
            lastAnalyzedTimeStamp = currentTimestamp
        } else {
            image.close()
        }
    }
}



fun parseLinkFromContent(scannedResult: MutableList<Barcode>): MutableList<String> {

    val urlPattern = ("((?:http|https)://)?(?:www\\.)?" +
            "[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]" +
            "{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)").toPattern()

    var urlList: MutableList<String>   = mutableListOf();
      scannedResult.forEach {
            value ->
    val matcher = value.rawValue?.let { urlPattern.matcher(it) }
    if (matcher != null) {
        while (matcher.find()){
             var url = matcher.group()
            Log.d("list00003",url.toString())
            urlList.add(url)
        }
    }
    return urlList
}
    return mutableListOf("emptylist");
}
