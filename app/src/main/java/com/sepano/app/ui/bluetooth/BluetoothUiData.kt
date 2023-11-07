package com.sepano.app.ui.bluetooth

import com.sepano.app.model.BluetoothDevice

data class BluetoothUiData(
    var scannedDevices: List<BluetoothDevice> = emptyList(),
    var pairedDevices: List<BluetoothDevice> = emptyList(),
)