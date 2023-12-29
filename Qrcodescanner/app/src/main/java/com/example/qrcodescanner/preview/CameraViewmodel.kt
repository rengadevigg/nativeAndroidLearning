package com.example.qrcodescanner.preview

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect

class CameraViewModel(): ViewModel(){
    var _scannedBarcodeList = MutableStateFlow<List<String>>(emptyList());
    var scannedBarcodeList: StateFlow<List<String>> = _scannedBarcodeList.asStateFlow()

}