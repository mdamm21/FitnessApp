package com.example.fitnessapp.main

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.fitnessapp.FirebaseViewModel

@Composable
fun NotificationMessage (vm: FirebaseViewModel ){
    val notifState = vm.popUpNotification.value
    val notifMessage = notifState?.getContentOrNull()
    if (notifMessage != null ){
        Toast.makeText(LocalContext.current, notifMessage, Toast.LENGTH_SHORT).show()
    }
}