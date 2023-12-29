package com.example.qrcodescanner.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScannedInfo(list: MutableList<String>){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //to show a last scanned value.
        Text(text = list[list.lastIndex])
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