package com.sepano.app.util

import android.bluetooth.BluetoothManager
import android.content.Context

fun isBluetoothOn(context: Context): Boolean {
    return context.getSystemService(BluetoothManager::class.java).adapter?.isEnabled
        ?: false
}

