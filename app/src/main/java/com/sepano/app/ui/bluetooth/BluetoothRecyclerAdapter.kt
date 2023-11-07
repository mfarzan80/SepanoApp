package com.sepano.app.ui.bluetooth

import android.bluetooth.BluetoothClass
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sepano.app.R
import com.sepano.app.databinding.BluetoothDeviceViewHolderBinding
import com.sepano.app.model.BluetoothData

//recycler adapter
class BluetoothRecyclerAdapter(private var deviceList: List<BluetoothData>) :
    RecyclerView.Adapter<BluetoothRecyclerAdapter.ViewHolder>() {

    class ViewHolder(binding: BluetoothDeviceViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BluetoothDeviceViewHolderBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(deviceList[position]) {
                BluetoothDeviceViewHolderBinding.bind(itemView).apply {
                    tvDeviceName.text = name
                    ivDeviceIcon.setImageResource(getDrawableIdByBluetoothClass(btClass))
                }
            }
        }
    }

    private fun getDrawableIdByBluetoothClass(bluetoothClass: BluetoothClass): Int {
        return when (bluetoothClass.deviceClass.and(0x1F00)) {

            BluetoothClass.Device.Major.AUDIO_VIDEO -> R.drawable.ic_headphone
            BluetoothClass.Device.Major.COMPUTER -> R.drawable.ic_monitor
            BluetoothClass.Device.Major.HEALTH -> R.drawable.ic_heart
            BluetoothClass.Device.Major.IMAGING -> R.drawable.ic_camera
            BluetoothClass.Device.Major.NETWORKING -> R.drawable.ic_global
            BluetoothClass.Device.Major.PHONE -> R.drawable.ic_mobile
            BluetoothClass.Device.Major.TOY -> R.drawable.ic_game
            BluetoothClass.Device.Major.WEARABLE -> R.drawable.ic_watch
            BluetoothClass.Device.Major.MISC -> R.drawable.ic_bluetooth
            BluetoothClass.Device.Major.PERIPHERAL -> R.drawable.ic_bluetooth
            BluetoothClass.Device.Major.UNCATEGORIZED -> R.drawable.ic_bluetooth

            else -> R.drawable.ic_bluetooth

        }
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    fun updateList(newList: List<BluetoothData>) {
        deviceList = newList
        notifyDataSetChanged()
    }
}