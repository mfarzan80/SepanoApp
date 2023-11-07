package com.sepano.app.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import java.util.Arrays


fun isAllBluetoothPermissionGranted(context: Context): Boolean {
    val permissions = getNotGrantedBluetoothPermissions(context)
    Log.d("Bluetooth", "isAllBluetoothPermissionGranted: ${permissions.contentToString()}")
    return permissions.isEmpty();
}


fun getNotGrantedBluetoothPermissions(context: Context): Array<String> {
    val permissions = mutableListOf<String>()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        //check for bluetooth permissions

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("Bluetooth", "checkForBluetoothPermissions: BLUETOOTH_CONNECT")
            permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
        }

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("Bluetooth", "checkForBluetoothPermissions: BLUETOOTH_SCAN")
            permissions.add(Manifest.permission.BLUETOOTH_SCAN)
        }

    }
    return permissions.toTypedArray()
}