package com.example.qrcodescanner.preview

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ScannedInfo( scannedList: List<String>) {
    val CameraViewModel = viewModel<CameraViewModel>();
    var scannedList: List<String> = listOf("")
    LaunchedEffect(key1 = CameraViewModel.scannedBarcodeList ) {
        Log.d("leoooooo78", CameraViewModel.scannedBarcodeList.value.toString())
          CameraViewModel.scannedBarcodeList.collect{
              Log.d("leoooooo88", CameraViewModel.scannedBarcodeList.value.toString())
             scannedList = it
         } }

    Log.d("leoooooo7", scannedList.toString())
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "hello")
            if (scannedList.isNotEmpty()) {
                Log.d("leooooo8", scannedList.toString())
                Text(text = scannedList.toString())
        }
    //to show a last scanned value.

}
}
        // to show a list of scanned value.
//    if(list.isNotEmpty()){
//        LazyColumn(
//            modifier = Modifier.fillMaxWidth(),
//            contentPadding = PaddingValues(16.dp)
//        ) {
//
//            items(list.size) { item ->
//                Text(text = item.toString())
//            }
//        }
//    }


//}