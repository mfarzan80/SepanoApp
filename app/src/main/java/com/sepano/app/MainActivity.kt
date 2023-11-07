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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.sepano.app.databinding.ActivityMainBinding
import com.sepano.app.ui.bluetooth.BluetoothFragment
import com.sepano.app.ui.calculator.CalculatorFragment
import com.sepano.app.ui.memory.MemoryFragment
import com.sepano.app.util.getNotGrantedBluetoothPermissions
import com.sepano.app.util.isAllBluetoothPermissionGranted
import com.sepano.app.util.isBluetoothOn
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() , NavigationBarView.OnItemSelectedListener{

    private val bluetoothFragment = BluetoothFragment()

    companion object {

        const val REQUEST_BT_PERMISSION = 1
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var startForResult: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                prepareBluetooth()

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
                if (result.data?.action == BluetoothAdapter.ACTION_REQUEST_ENABLE) {
                    prepareBluetooth()
                }
            }
        }
    }

    private fun prepareBluetooth() {
        if (!isAllBluetoothPermissionGranted(this))
            requestForBluetoothPermission()
        else if (!isBluetoothOn(this))
            turnOnBluetooth()
        else
            restartBluetoothController()
    }


    private fun requestForBluetoothPermission() {
        getNotGrantedBluetoothPermissions(this).let {
            if (it.isNotEmpty())
                requestPermissions(it, REQUEST_BT_PERMISSION)
        }
    }

    private fun restartBluetoothController() {
        if (isAllBluetoothPermissionGranted(this) && isBluetoothOn(this)) {
            bluetoothFragment.startScan()
        }
    }

    private fun turnOnBluetooth() {
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startForResult.launch(enableBtIntent)
    }

    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.navView
        navView.setOnItemSelectedListener(this);
    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main, fragment).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment = when (item.itemId) {
            R.id.navigation_bluetooth -> bluetoothFragment
            R.id.navigation_calculator -> CalculatorFragment()
            R.id.navigation_memory -> MemoryFragment()
            else -> bluetoothFragment
        }
        displayFragment(fragment)
        return true
    }

}