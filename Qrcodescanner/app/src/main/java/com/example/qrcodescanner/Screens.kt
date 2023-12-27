package com.example.qrcodescanner

sealed class Screen(val route: String){
    object Home: Screen("Home")
    object Camera: Screen("open_camera")
    object ScannedInfo: Screen("Scanned_Info")
}
