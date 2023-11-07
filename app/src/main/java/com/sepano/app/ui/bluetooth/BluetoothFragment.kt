package com.sepano.app.ui.bluetooth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sepano.app.MainActivity
import com.sepano.app.R
import com.sepano.app.data.ApiResult
import com.sepano.app.data.ApiStatus
import com.sepano.app.databinding.FragmentBluetoothBinding
import com.sepano.app.exception.BluetoothNotEnabledException
import com.sepano.app.exception.BluetoothNotSupportedException
import com.sepano.app.exception.BluetoothPermissionException
import com.sepano.app.util.startInstalledAppDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BluetoothFragment() : Fragment() {


    private var _binding: FragmentBluetoothBinding? = null


    private val binding get() = _binding!!
    private lateinit var context: Context
    private val mainActivity get() = requireActivity() as MainActivity

    private val viewModel: BluetoothViewModel by activityViewModels()

    private val scannedDevicesRecyclerViewAdapter = BluetoothRecyclerAdapter(listOf())
    private val pairedDevicesRecyclerViewAdapter = BluetoothRecyclerAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentBluetoothBinding.inflate(inflater, container, false)
        val root: View = binding.root


        startScan()

        setupRecyclers()

        setClickListeners()

        lifecycleScope.launch {
            launch {
                viewModel.state.collect { state ->
                    manageStates(state)
                }
            }
            viewModel.bluetoothDevices.collect {
                scannedDevicesRecyclerViewAdapter.updateList(it.scannedDevices)
                pairedDevicesRecyclerViewAdapter.updateList(it.pairedDevices)
            }
        }

        return root
    }

    private fun setClickListeners() {

        binding.ivRefresh.setOnClickListener {
            if (viewModel.state.value.status != ApiStatus.LOADING) {
                startScan()
            }
        }
        binding.btnTurnOnBluetooth.setOnClickListener {
            mainActivity.turnOnBluetooth()
        }
        binding.btnGrantPermission.setOnClickListener {
            mainActivity.requestForBluetoothPermission()
        }
        binding.btnOpenSettings.setOnClickListener {
            startInstalledAppDetailsActivity(context)
        }
    }

    private fun manageStates(state: ApiResult<Unit>) {
        when (state.status) {
            ApiStatus.ERROR -> {
                when (state.exception) {
                    is BluetoothPermissionException -> {
                        permissionErrorState()
                    }

                    is BluetoothNotEnabledException -> {
                        bluetoothOffErrorState()
                    }

                    is BluetoothNotSupportedException -> {
                        bluetoothNotSupportedErrorState()
                    }

                    else -> {
                        bluetoothOffErrorState()
                    }
                }
            }

            ApiStatus.SUCCESS -> {
                successState()
            }

            ApiStatus.LOADING -> {
                loadingState()
            }

        }

    }

    private fun setupRecyclers() {

        binding.rvScannedBluetoothDevices.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvPairedBluetoothDevices.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.rvScannedBluetoothDevices.adapter = scannedDevicesRecyclerViewAdapter
        binding.rvPairedBluetoothDevices.adapter = pairedDevicesRecyclerViewAdapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun startScan() {
        viewModel.startScan()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    private fun permissionErrorState() {
        binding.llPermissionErrorContainer.visibility = View.VISIBLE
        binding.llBluetoothOffContainer.visibility = View.GONE
        binding.nsvScannedDevices.visibility = View.GONE
    }

    private fun bluetoothOffErrorState() {
        binding.llPermissionErrorContainer.visibility = View.GONE
        binding.llBluetoothOffContainer.visibility = View.VISIBLE
        binding.tvBluetoothOffTitle.setText(R.string.bluetooth_off_title)
        binding.tvBluetoothOffText.setText(R.string.bluetooth_off_text)
        binding.btnTurnOnBluetooth.visibility = View.VISIBLE
        binding.nsvScannedDevices.visibility = View.GONE
    }

    private fun bluetoothNotSupportedErrorState() {
        binding.llPermissionErrorContainer.visibility = View.GONE
        binding.llBluetoothOffContainer.visibility = View.VISIBLE
        binding.tvBluetoothOffTitle.setText(R.string.bluetooth_not_support_title)
        binding.tvBluetoothOffText.setText(R.string.bluetooth_not_support_text)
        binding.btnTurnOnBluetooth.visibility = View.GONE
        binding.nsvScannedDevices.visibility = View.GONE
    }

    private fun successState() {
        binding.llPermissionErrorContainer.visibility = View.GONE
        binding.llBluetoothOffContainer.visibility = View.GONE
        binding.nsvScannedDevices.visibility = View.VISIBLE
        binding.ivRefresh.clearAnimation()
    }

    private fun loadingState() {
        binding.llPermissionErrorContainer.visibility = View.GONE
        binding.llBluetoothOffContainer.visibility = View.GONE
        binding.nsvScannedDevices.visibility = View.VISIBLE
        binding.ivRefresh.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotation))
    }

}