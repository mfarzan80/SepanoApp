package com.sepano.app

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.sepano.app.databinding.ActivityMainBinding
import com.sepano.app.ui.bluetooth.BluetoothFragment
import com.sepano.app.ui.bluetooth.BluetoothViewModel
import com.sepano.app.ui.calculator.CalculatorFragment
import com.sepano.app.ui.memory.PhoneFragment
import com.sepano.app.util.getNotGrantedBluetoothPermissions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var bluetoothViewModel: BluetoothViewModel

    companion object {

        const val REQUEST_BT_PERMISSION = 1
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var startForResult: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bluetoothViewModel = ViewModelProvider(this)[BluetoothViewModel::class.java]

        registerActivityResult()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_BT_PERMISSION) {
            val notGrantedPermissions = getNotGrantedBluetoothPermissions(this)
            if (notGrantedPermissions.isEmpty()) {
                bluetoothViewModel.startScan()

            } else {
                Log.d(
                    "Bluetooth",
                    "onRequestPermissionsResult: permissions denied: ${notGrantedPermissions.contentToString()}"
                )
            }
        }
    }

    private fun registerActivityResult() {
        startForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->

            if (result.resultCode == Activity.RESULT_OK) {
                bluetoothViewModel.startScan()
            }
        }
    }


    fun requestForBluetoothPermission() {
        getNotGrantedBluetoothPermissions(this).let {
            if (it.isNotEmpty())
                requestPermissions(it, REQUEST_BT_PERMISSION)
        }
    }

    fun turnOnBluetooth() {
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startForResult.launch(enableBtIntent)
    }

    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.navView
        navView.setOnItemSelectedListener(this);
    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, fragment).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment = when (item.itemId) {
            R.id.navigation_bluetooth -> BluetoothFragment()
            R.id.navigation_calculator -> CalculatorFragment()
            R.id.navigation_phone -> PhoneFragment()
            else -> BluetoothFragment()
        }
        displayFragment(fragment)
        return true
    }

}