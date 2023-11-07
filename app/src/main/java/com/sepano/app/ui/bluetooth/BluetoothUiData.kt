package com.sepano.app.ui.bluetooth

import com.sepano.app.model.BluetoothData

data class BluetoothUiData(
    var scannedDevices: List<BluetoothData> = emptyList(),
    var pairedDevices: List<BluetoothData> = emptyList(),
)