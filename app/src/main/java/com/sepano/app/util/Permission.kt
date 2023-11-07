package com.sepano.app.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat


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

fun startInstalledAppDetailsActivity(context: Context) {
    val i = Intent()
    i.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    i.addCategory(Intent.CATEGORY_DEFAULT)
    i.data = Uri.parse("package:" + context.packageName)
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    context.startActivity(i)
}