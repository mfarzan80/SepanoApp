package com.sepano.app.ui.calculator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sepano.app.databinding.ViewHolderCalculatorButtonBinding
import com.sepano.app.databinding.FragmentCalculatorBinding
import com.sepano.app.util.getMaterialColor
import com.sepano.app.util.getScreenWidth


class CalculatorFragment : Fragment() {

    companion object {
        const val CLEAR_CHAR = 'C'
    }

    private var _binding: FragmentCalculatorBinding? = null


    private val binding get() = _binding!!


    private val viewModel: CalculatorViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.pendingDisplay.observe(viewLifecycleOwner) { value ->
            binding.tvPendingDisplay.text = value
        }
        viewModel.display.observe(viewLifecycleOwner) { value ->
            binding.tvDisplay.text = value
        }

        addButtons()

        return root
    }


    private fun addButtons() {
        val context = requireContext()
        val buttons = listOf(
            '7', '8', '9', '-',
            '4', '5', '6', '+',
            '1', '2', '3', '*',
            CLEAR_CHAR, '0', '=', '/'
        )

        val screenWidth = getScreenWidth(context)
        //get dimen
        val btnSize = screenWidth / 5


        binding.glButtons.layoutParams = LinearLayout.LayoutParams(
            screenWidth,
            screenWidth
        )

        Log.d("CalculatorFragment", "addButtons: $btnSize")
        // create view of each button and add it to the grid
        for (btn in buttons) {
            val onClick: () -> Unit
            val backColor: Int
            val foreColor: Int
            when (btn) {
                in '0'..'9' -> {
                    backColor = getMaterialColor(context, com.google.android.material.R.attr.colorSurface)
                    foreColor = getMaterialColor(context, com.google.android.material.R.attr.colorOnSurface)
                    onClick = { viewModel.onDigitPressed(btn) }
                }

                CLEAR_CHAR -> {
                    backColor = getMaterialColor(context, com.google.android.material.R.attr.colorError)
                    foreColor = getMaterialColor(context, com.google.android.material.R.attr.colorOnError)
                    onClick = { viewModel.onClear() }
                }

                '=' -> {
                    backColor = getMaterialColor(context, com.google.android.material.R.attr.colorPrimary)
                    foreColor = getMaterialColor(context, com.google.android.material.R.attr.colorOnPrimary)
                    onClick = { viewModel.onEqualsPressed() }
                }
                else ->{
                    backColor = getMaterialColor(context, com.google.android.material.R.attr.colorSecondary)
                    foreColor = getMaterialColor(context, com.google.android.material.R.attr.colorOnSecondary)
                    onClick = { viewModel.onOperatorPressed(btn)}
                }
            }
            val buttonBinding = ViewHolderCalculatorButtonBinding.inflate(layoutInflater)
            buttonBinding.tvText.text = "$btn"
            buttonBinding.tvText.setTextColor(foreColor)
            buttonBinding.cvButton.setOnClickListener { onClick() }
            buttonBinding.cvButton.layoutParams = GridLayout.LayoutParams(
                GridLayout.spec(
                    GridLayout.UNDEFINED,
                    GridLayout.CENTER,
                    1f
                ),
                GridLayout.spec(GridLayout.UNDEFINED, GridLayout.CENTER, 1f)
            ).apply {
                width = btnSize
                height = btnSize
            }
            buttonBinding.cvButton.setCardBackgroundColor(backColor)
            binding.glButtons.addView(buttonBinding.cvButton)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}