package com.example.qrcodescanner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.qrcodescanner.preview.PreviewViewComposable
import com.example.qrcodescanner.shared.ScannedInfo
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
fun  AppNavGraph(navController: NavHostController){
    val scannedBarcode = remember { mutableListOf<String>("hello","hello1") }
    NavHost(navController = navController, startDestination = Screen.Home.route){
        composable(Screen.Home.route){
            Home(navController = navController)
        }
        composable(Screen.Camera.route){
            PreviewViewComposable(navController , onBardCodeScanner = {
                    barcode -> scannedBarcode.add(barcode.toString())
            })
        }
        composable(Screen.ScannedInfo.route){
            ScannedInfo(scannedBarcode)
        }
    }
}