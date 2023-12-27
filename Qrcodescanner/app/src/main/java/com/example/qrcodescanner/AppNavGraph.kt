package com.example.qrcodescanner

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.qrcodescanner.preview.PreviewViewComposable
import com.example.qrcodescanner.shared.PermissionScreen
import com.example.qrcodescanner.shared.ScannedInfo
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
fun  AppNavGraph(navController: NavHostController){
    val scannedBarcode = remember { mutableStateOf<String?>(null) }
    NavHost(navController = navController, startDestination = Screen.Home.route){
        composable(Screen.Home.route){
            Home(navController = navController)
        }
        composable(Screen.Camera.route){
            PreviewViewComposable(navController , onBardCodeScanner = {
                    barcode -> scannedBarcode.value = barcode
            })
        }
        composable(Screen.ScannedInfo.route){
            ScannedInfo(scannedBarcode.value.toString())
        }
    }
}