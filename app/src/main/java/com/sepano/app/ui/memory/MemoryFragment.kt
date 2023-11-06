package com.sepano.app.ui.memory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sepano.app.databinding.FragmentMemoryBinding

class MemoryFragment : Fragment() {

    private var _binding: FragmentMemoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val memoryViewModel =
            ViewModelProvider(this)[MemoryViewModel::class.java]

        _binding = FragmentMemoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        memoryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}