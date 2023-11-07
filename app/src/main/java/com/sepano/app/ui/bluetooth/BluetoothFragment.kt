package com.sepano.app.ui.bluetooth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sepano.app.databinding.FragmentBluetoothBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BluetoothFragment() : Fragment() {


    private var _binding: FragmentBluetoothBinding? = null


    private val binding get() = _binding!!
    private lateinit var context: Context

    private val viewModel by viewModels<BluetoothViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentBluetoothBinding.inflate(inflater, container, false)
        val root: View = binding.root
        context = requireContext()

        startScan()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun startScan() {
        viewModel.startScan()
    }


}