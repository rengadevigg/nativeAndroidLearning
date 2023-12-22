package com.example.qrcodescanner

sealed class Screen(val route: String){
    object Home: Screen("Home")
    object Camera: Screen("open_camera")
    object Info: Screen("scanned_info")
    object RequestPermission: Screen("req_per")
}
