package com.example.qrcodescanner.preview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class CameraViewModel(): ViewModel(){
    var _scannedBarcodeList = MutableStateFlow(emptyList<String>().toMutableList());
    val scannedBarcodeList  = _scannedBarcodeList.asStateFlow()

    fun updateListState(newList: MutableList<String>) {



                    _scannedBarcodeList.update {
                        it.addAll(newList)
                        return@update it
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

