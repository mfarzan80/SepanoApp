package com.sepano.app.ui.memory

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sepano.app.databinding.FragmentPhoneBinding
import com.sepano.app.util.getMemoryInfo
import com.sepano.app.util.getScreenHeight
import com.sepano.app.util.getStorageInfo
import com.sepano.app.util.toPrecision

class PhoneFragment : Fragment() {

    private var _binding: FragmentPhoneBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPhoneBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setProgressViews()

        return root
    }

    @SuppressLint("SetTextI18n")
    private fun setProgressViews() {
        val context = requireContext()
        val progressViewHeight = getScreenHeight(context) * 40 / 100
        binding.pvStorage.layoutParams.height = progressViewHeight
        binding.pvMemory.layoutParams.height = progressViewHeight

        val memoryInfo = getMemoryInfo(context)
        val storageInfo = getStorageInfo()

        val memoryAvailableInGB: Float = memoryInfo.availableSpace / 1024 / 1024 / 1024.toFloat()
        val memoryTotalInGB: Float = memoryInfo.totalSpace / 1024 / 1024 / 1024.toFloat()
        val memoryProgress = memoryTotalInGB - memoryAvailableInGB
        val memoryPercentage: Int = (memoryProgress / memoryTotalInGB * 100).toInt()

        val storageAvailableInGB: Float = storageInfo.availableSpace / 1024 / 1024 / 1024.toFloat()
        val storageTotalInGB: Float = storageInfo.totalSpace / 1024 / 1024 / 1024.toFloat()
        val storageProgress = storageTotalInGB - storageAvailableInGB
        val storagePercentage: Int = (storageProgress / storageTotalInGB * 100).toInt()



        binding.pvMemory.max = 100f
        binding.pvMemory.progress = memoryPercentage.toFloat()
        binding.pvMemory.labelText = "$memoryPercentage%"
        binding.tvMemoryDetail.text =
            "${memoryAvailableInGB.toPrecision(2)} GB\n free of\n${memoryTotalInGB.toPrecision(2)} GB"


        binding.pvStorage.max = 100f
        binding.pvStorage.progress = storagePercentage.toFloat()
        binding.pvStorage.labelText = "$storagePercentage%"
        binding.tvStorageDetail.text =
            "${storageAvailableInGB.toPrecision(2)} GB\n free of\n${storageTotalInGB.toPrecision(2)} GB"

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

