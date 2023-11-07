package com.sepano.app.exception

abstract class BluetoothException : Exception()

class BluetoothNotEnabledException : BluetoothException()

class BluetoothNotSupportedException : BluetoothException()

class BluetoothPermissionException(val notGrantedPermission: List<String>) : BluetoothException()