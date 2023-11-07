package com.sepano.app.ui.bluetooth

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sepano.app.data.ApiStatus
import com.sepano.app.exception.BluetoothException
import com.sepano.app.ui.ApiViewModel
import com.sepano.app.util.BluetoothController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BluetoothViewModel @Inject constructor(private val bluetoothController: BluetoothController) :
    ApiViewModel<Unit>() {
    companion object{
        const val SCANNING_TIME = 12000L
    }

    private var timer: Job? = null;

    private val _bluetoothDevices = MutableStateFlow(BluetoothUiData())
    val bluetoothDevices = combine(
        bluetoothController.scannedDevices,
        bluetoothController.pairedDevices,
        _bluetoothDevices
    ) { scannedDevices, pairedDevices, bluetoothUiData ->
        Log.d("Bluetooth", "BluetoothViewModel: Combined $scannedDevices")
        bluetoothUiData.copy(
            scannedDevices = scannedDevices,
            pairedDevices = pairedDevices
        )
    }.stateIn(viewModelScope , SharingStarted.WhileSubscribed(SCANNING_TIME) , _bluetoothDevices.value)


    fun startScan() {
        startLoading()
        try {
            bluetoothController.startScan()
            startScanningTimer()
        } catch (e: BluetoothException) {
            Log.e("Bluetooth", "BluetoothViewModel: startScan: " , e)
            error(e)
        }
    }

    private fun stopScan() {
        bluetoothController.stopScan()
        timer?.cancel()
    }

    private fun startScanningTimer(){
        timer?.cancel()
        timer = viewModelScope.launch {
            delay(SCANNING_TIME)
            if(state.value.status == ApiStatus.LOADING){
                success(Unit)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopScan()
        bluetoothController.release()
    }
}