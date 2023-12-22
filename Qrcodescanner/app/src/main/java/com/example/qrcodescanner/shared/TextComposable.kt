package com.example.qrcodescanner.shared

import android.content.Intent
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import android.net.Uri

@Composable
fun ShowText(info: String, showDialog: Boolean, confirmAction: () -> Unit ){
    val showDialog = remember { mutableStateOf(showDialog) }
    val context = LocalContext.current

    if(showDialog.value){
        AlertDialog(
            title = { Text("PermissionRequest") },
            text = { Text (text = "We need camera permission to be authorized to scan a code. Please go to app settings and e" +
                    "enable the camera permission") },
            confirmButton = { TextButton(onClick = { showDialog.value = false
                    context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    })
            }) {
                Text(text = "Open App Settings")

            } },
            onDismissRequest = {showDialog.value = false}
        )
    }



}


