package com.sepano.app.util

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.IntentCompat
import com.sepano.app.model.BluetoothDevice

class BluetoothReceiver(private val onReceiveDevice : (BluetoothDevice) -> Unit) : BroadcastReceiver(){

    @SuppressLint("MissingPermission") // checkForBluetoothPermissions check permission
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            android.bluetooth.BluetoothDevice.ACTION_FOUND -> {
                val device: android.bluetooth.BluetoothDevice? = IntentCompat.getParcelableExtra(
                    intent,
                    android.bluetooth.BluetoothDevice.EXTRA_DEVICE,
                    android.bluetooth.BluetoothDevice::class.java
                )
                Log.d("Bluetooth", "onReceive: checkForBluetoothPermissions")
                if (isAllBluetoothPermissionGranted(context)) {
                    Log.d("Bluetooth", "onReceive: isAllBluetoothPermissionGranted true")
                    if(device != null && device.name != null) {
                        val bluetoothDevice = BluetoothDevice(device.name, device.address)
                        onReceiveDevice(bluetoothDevice)
                    }
                    Log.d("Bluetooth", "onReceive: ${device?.name}")
                } else {
                    Log.d("Bluetooth", "onReceive: user permission denied")
                }
            }
        }
    }
}