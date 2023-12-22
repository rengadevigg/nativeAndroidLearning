package com.example.qrcodescanner

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.qrcodescanner.preview.PreviewViewComposable
import com.example.qrcodescanner.shared.PermissionScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
fun  AppNavGraph(navController: NavHostController){
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    NavHost(navController = navController, startDestination = Screen.Home.route){
        composable(Screen.Home.route){
            Home(navController = navController)
        }
        composable(Screen.Camera.route){
            if(permissionState.status.isGranted){
                PreviewViewComposable()
            }else{
                PermissionScreen(permissionStatus = permissionState,
                    navController)
            }
        }
        composable(Screen.RequestPermission.route){
            PermissionScreen(permissionStatus = permissionState,
                navController)
        }
    }
}