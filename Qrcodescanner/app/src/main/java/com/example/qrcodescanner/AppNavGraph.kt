package com.example.qrcodescanner

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
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
    val viewModel = viewModel<CameraViewModel>()
    val scannedList by viewModel.scannedBarcodeList.collectAsState()
    NavHost(navController = navController, startDestination = Screen.Home.route){
        composable(Screen.Home.route){
            Home(navController = navController)
        }
        composable(Screen.Camera.route){
            PreviewViewComposable()
        }
        composable(Screen.ScannedList.route){
            ScannedInfo(list = scannedList)
        }
    }
}