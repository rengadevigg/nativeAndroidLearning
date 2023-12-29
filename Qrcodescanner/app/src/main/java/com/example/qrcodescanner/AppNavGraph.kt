package com.example.qrcodescanner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.qrcodescanner.preview.CameraViewModel
import com.example.qrcodescanner.preview.PreviewViewComposable
import com.example.qrcodescanner.shared.ScannedInfo
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
fun  AppNavGraph(navController: NavHostController){
    val viewModel = CameraViewModel()
    val scannedList by viewModel.scannedBarcodeList.collectAsState()
    NavHost(navController = navController, startDestination = Screen.Home.route){
        composable(Screen.Home.route){
            Home(navController = navController)
        }
        composable(Screen.Camera.route){
            PreviewViewComposable(navController , onBardCodeScanner = {
                    barcode -> viewModel._scannedBarcodeList.value = listOf(barcode.toString())
            })
        }
        composable(Screen.ScannedInfo.route){
            ScannedInfo(scannedList)
        }
    }
}