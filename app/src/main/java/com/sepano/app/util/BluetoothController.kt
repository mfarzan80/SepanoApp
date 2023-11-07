package com.sepano.app.util

import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.IntentFilter
import android.util.Log
import com.sepano.app.exception.BluetoothNotEnabledException
import com.sepano.app.exception.BluetoothNotSupportedException
import com.sepano.app.exception.BluetoothPermissionException
import com.sepano.app.model.BluetoothData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@SuppressLint("MissingPermission") // checkForBluetoothPermissions check permission
class BluetoothController(private val context: Context) {


    private val bluetoothManager by lazy { context.getSystemService(BluetoothManager::class.java) }
    private val bluetoothAdapter by lazy { bluetoothManager.adapter }

    private val bluetoothReceiver = BluetoothReceiver { device ->
        Log.d("Bluetooth", "BluetoothReceiver: ${device.name}")
        _scannedDevices.update { devices ->
            if (devices.contains(device)) {
                devices
            } else {
                devices + device
            }
        }
    };

    private val _scannedDevices = MutableStateFlow<List<BluetoothData>>(emptyList())
    val scannedDevices: StateFlow<List<BluetoothData>>
        get() = _scannedDevices.asStateFlow()

    private val _pairedDevices = MutableStateFlow<List<BluetoothData>>(emptyList())
    val pairedDevices: StateFlow<List<BluetoothData>>
        get() = _pairedDevices.asStateFlow()

    fun startScan() {
        Log.d("Bluetooth", "startBluetooth: starting bluetooth")

        assertBluetoothCanScan()

        updatePairedDevices()
        val filter = IntentFilter(android.bluetooth.BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(bluetoothReceiver, filter)
        bluetoothAdapter?.startDiscovery()


    }


    fun stopScan() {
        bluetoothAdapter?.cancelDiscovery()
    }

    fun release() {
        if (bluetoothReceiver.isOrderedBroadcast) {
            context.unregisterReceiver(bluetoothReceiver)
        }

    }

    private fun updatePairedDevices() {

        bluetoothAdapter?.bondedDevices?.map { device ->
            BluetoothData(device.name, device.bluetoothClass)
        }?.also { newDevices ->
            _pairedDevices.update { newDevices }
        }

    }

    private fun assertBluetoothCanScan() {
        if (bluetoothAdapter == null)
            throw BluetoothNotSupportedException()

        val notGrantedPermission = getNotGrantedBluetoothPermissions(context)
        if (notGrantedPermission.isNotEmpty())
            throw BluetoothPermissionException(notGrantedPermission.toList())

        if (!bluetoothAdapter!!.isEnabled)
            throw BluetoothNotEnabledException()

    }


}