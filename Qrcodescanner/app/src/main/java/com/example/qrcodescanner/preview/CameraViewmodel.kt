package com.example.qrcodescanner.preview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CameraViewModel(): ViewModel(){
    var _scannedBarcodeList = MutableStateFlow<MutableList<String>>(emptyList<String>().toMutableList());
    val scannedBarcodeList: StateFlow<MutableList<String>> get() = _scannedBarcodeList

    fun updateListState(newList: String) {
        viewModelScope.launch {
            _scannedBarcodeList.value = _scannedBarcodeList.value.apply {
                add(newList)
            }
        }
    }

    private val _isScanningEnabled = MutableStateFlow(true)
    val isScanningEnabled: StateFlow<Boolean> get() = _isScanningEnabled

    fun updateScanningState(isEnabled: Boolean = true) {
        viewModelScope.launch {
            _isScanningEnabled.value = isEnabled
        }
    }

}