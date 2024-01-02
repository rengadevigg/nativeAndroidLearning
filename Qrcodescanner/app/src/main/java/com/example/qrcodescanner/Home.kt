package com.example.qrcodescanner

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.qrcodescanner.preview.CameraViewModel
import com.example.qrcodescanner.preview.PreviewViewComposable
import com.example.qrcodescanner.shared.ShowText
import com.example.qrcodescanner.ui.theme.CookeryColor
import com.example.qrcodescanner.ui.theme.LightCookeryColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@Composable
fun Home(navController: NavHostController) {
    Scaffold(navController)
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OpenScanner(navController: NavHostController){
val permissionState = rememberPermissionState(permission = android.Manifest.permission.CAMERA)
    val showAlert = remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(30.dp)
        ) {
            Text(
                text = "Press a button to scan a code",
                modifier = Modifier.padding(top = 50.dp),
            )
        }
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            TextButton(
                onClick =
                {

                    if (!permissionState.status.isGranted) {
                    if (!permissionState.status.shouldShowRationale) {
                        permissionState.launchPermissionRequest()
                    }
                    else if(permissionState.status.shouldShowRationale){
                        showAlert.value = true
                    }
                }else if (permissionState.status.isGranted){
                    navController.navigate(Screen.Camera.route)
                }
                },
                modifier = Modifier
                    .background(color = LightCookeryColor)
                    .width(250.dp)

            ) {
                Text("Open Scanner".uppercase(),
                    color = Color.White
                )
            }
        }

    }
    if (showAlert.value){
        ShowText(info = "hello", showDialog = true
        ) {
            navController.navigate(Screen.Home.route)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scaffold(navController: NavHostController){
    androidx.compose.material3.Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.background(color = CookeryColor),
                title = {
                    Text(
                        "Barcode Scanner".uppercase(),
                        fontFamily = FontFamily.SansSerif,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = CookeryColor)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            OpenScanner(navController)
        }
    }
}