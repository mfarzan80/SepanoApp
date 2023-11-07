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
        return when (bluetoothClass.deviceClass) {
            BluetoothClass.Device.AUDIO_VIDEO_CAMCORDER -> R.drawable.ic_headphone
            BluetoothClass.Device.AUDIO_VIDEO_CAR_AUDIO -> R.drawable.ic_headphone
            BluetoothClass.Device.AUDIO_VIDEO_HANDSFREE -> R.drawable.ic_headphone
            BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES -> R.drawable.ic_headphone
            BluetoothClass.Device.AUDIO_VIDEO_HIFI_AUDIO -> R.drawable.ic_headphone
            BluetoothClass.Device.AUDIO_VIDEO_LOUDSPEAKER -> R.drawable.ic_headphone
            BluetoothClass.Device.AUDIO_VIDEO_MICROPHONE -> R.drawable.ic_headphone
            BluetoothClass.Device.AUDIO_VIDEO_PORTABLE_AUDIO -> R.drawable.ic_headphone
            BluetoothClass.Device.AUDIO_VIDEO_SET_TOP_BOX -> R.drawable.ic_headphone
            BluetoothClass.Device.AUDIO_VIDEO_UNCATEGORIZED -> R.drawable.ic_headphone
            BluetoothClass.Device.AUDIO_VIDEO_VCR -> R.drawable.ic_headphone
            BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CAMERA -> R.drawable.ic_headphone
            BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CONFERENCING -> R.drawable.ic_headphone
            BluetoothClass.Device.AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER -> R.drawable.ic_headphone
            BluetoothClass.Device.AUDIO_VIDEO_VIDEO_GAMING_TOY -> R.drawable.ic_headphone
            BluetoothClass.Device.AUDIO_VIDEO_VIDEO_MONITOR -> R.drawable.ic_headphone
            BluetoothClass.Device.AUDIO_VIDEO_WEARABLE_HEADSET -> R.drawable.ic_headphone
            BluetoothClass.Device.COMPUTER_DESKTOP -> R.drawable.ic_monitor
            BluetoothClass.Device.COMPUTER_HANDHELD_PC_PDA -> R.drawable.ic_monitor
            BluetoothClass.Device.COMPUTER_LAPTOP -> R.drawable.ic_monitor
            BluetoothClass.Device.COMPUTER_PALM_SIZE_PC_PDA -> R.drawable.ic_monitor
            BluetoothClass.Device.COMPUTER_SERVER -> R.drawable.ic_monitor
            BluetoothClass.Device.COMPUTER_UNCATEGORIZED -> R.drawable.ic_monitor
            BluetoothClass.Device.COMPUTER_WEARABLE -> R.drawable.ic_monitor
            BluetoothClass.Device.HEALTH_BLOOD_PRESSURE -> R.drawable.ic_bluetooth
            BluetoothClass.Device.HEALTH_DATA_DISPLAY -> R.drawable.ic_bluetooth
            BluetoothClass.Device.HEALTH_GLUCOSE -> R.drawable.ic_bluetooth
            BluetoothClass.Device.HEALTH_PULSE_OXIMETER -> R.drawable.ic_bluetooth
            BluetoothClass.Device.HEALTH_PULSE_RATE -> R.drawable.ic_bluetooth
            BluetoothClass.Device.HEALTH_THERMOMETER -> R.drawable.ic_bluetooth
            BluetoothClass.Device.HEALTH_UNCATEGORIZED -> R.drawable.ic_bluetooth
            BluetoothClass.Device.HEALTH_WEIGHING -> R.drawable.ic_bluetooth
            BluetoothClass.Device.PHONE_CELLULAR -> R.drawable.ic_mobile
            BluetoothClass.Device.PHONE_CORDLESS -> R.drawable.ic_mobile
            BluetoothClass.Device.PHONE_ISDN -> R.drawable.ic_mobile
            BluetoothClass.Device.PHONE_MODEM_OR_GATEWAY -> R.drawable.ic_mobile
            BluetoothClass.Device.PHONE_SMART -> R.drawable.ic_mobile
            BluetoothClass.Device.PHONE_UNCATEGORIZED -> R.drawable.ic_mobile
            BluetoothClass.Device.TOY_CONTROLLER -> R.drawable.ic_bluetooth
            BluetoothClass.Device.TOY_DOLL_ACTION_FIGURE -> R.drawable.ic_bluetooth
            BluetoothClass.Device.TOY_GAME -> R.drawable.ic_bluetooth
            BluetoothClass.Device.TOY_ROBOT -> R.drawable.ic_bluetooth
            BluetoothClass.Device.TOY_UNCATEGORIZED -> R.drawable.ic_bluetooth
            BluetoothClass.Device.TOY_VEHICLE -> R.drawable.ic_bluetooth
            BluetoothClass.Device.WEARABLE_GLASSES -> R.drawable.ic_bluetooth
            BluetoothClass.Device.WEARABLE_HELMET -> R.drawable.ic_bluetooth
            BluetoothClass.Device.WEARABLE_JACKET -> R.drawable.ic_bluetooth
            BluetoothClass.Device.WEARABLE_PAGER -> R.drawable.ic_bluetooth
            BluetoothClass.Device.WEARABLE_UNCATEGORIZED -> R.drawable.ic_bluetooth
            BluetoothClass.Device.WEARABLE_WRIST_WATCH -> R.drawable.ic_watch




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