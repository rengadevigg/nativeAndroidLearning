package com.example.qrcodescanner.shared


import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.regex.Pattern

class BarcodeAnalyser(
    val context: Context,
    val callback: (barcode: String) -> Unit
) : ImageAnalysis.Analyzer {
    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    override fun  analyze(imageProxy: ImageProxy) {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .build()

        val scanner = BarcodeScanning.getClient(options)
        val mediaImage = imageProxy.image
        mediaImage?.let {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty()) {
                        val barcode = barcodes[0]
                        val barcodeValue = barcode.rawValue
                        if (barcodeValue != null) {
                            val parsedLink = parseLinkFromContent(barcodeValue)
                            callback(parsedLink)
                        }
                    }
                    Log.d("success", "sucess")
                }

                .addOnFailureListener {
                    Log.d("success", "failure")
                    Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT)
                }
                .addOnCanceledListener {
                    Log.d("success", "canceled")
                }
                .addOnCompleteListener {
                    Log.d("success", "complete")
                    Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT)
                }
        }
        imageProxy.close()
    }
}

fun parseLinkFromContent(scannedResult: String): String {
    val urlPattern = Pattern.compile(
        "\\bhttps?://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"
    )

    val matcher = urlPattern.matcher(scannedResult)
    while (matcher.find()){
        val url = matcher.group()
        return url;
    }
    return "no url found";

}