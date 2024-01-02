package com.example.qrcodescanner.shared

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ScannedInfo(list: MutableList<String>){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //to show a last scanned value.
        if(list.isNotEmpty()){
        Text(text = list[list.lastIndex])
        }

        // to show a list of scanned value.
//        LazyColumn(
//            modifier = Modifier.fillMaxWidth(),
//            contentPadding = PaddingValues(16.dp)
//        ) {
//            items(list) { item ->
//                Text(text = item)
//            }
//        }
    }
}