package com.example.week14_boradcastreceiver

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.week14_boradcastreceiver.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val broadcastReceiver = MyBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        
        //버튼을 누르면 방송보냄
        binding.button.setOnClickListener {
            sendBroadcast(Intent(ACTION_MY_BROADCAST))
        }

        //문자 수신에 대한 권한 허가 메서드
        requestSinglePermission(Manifest.permission.RECEIVE_SMS)
    }

    private fun requestSinglePermission(permission: String) {
        //이미 권한이 부여됐다면 return
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)
            return


        val requestPermLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it == false) { // permission is not granted!
                AlertDialog.Builder(this).apply {
                    setTitle("Warning")
                    setMessage(getString(R.string.no_permission, permission))
                }.show()
            }
        }
        //권한이 부여되지 않았다면 다이얼로그를 띄움
        if (shouldShowRequestPermissionRationale(permission)) {
            // you should explain the reason why this app needs the permission.
            AlertDialog.Builder(this).apply {
                setTitle("Reason")
                setMessage(getString(R.string.req_permission_reason, permission))
                setPositiveButton("Allow") { _, _ -> requestPermLauncher.launch(permission) }
                setNegativeButton("Deny") { _, _ -> }
            }.show()
        } else {
            // should be called in onCreate()
            requestPermLauncher.launch(permission)
        }
    }

    private fun startBroadcastReceiver() {
        IntentFilter().also {
            it.addAction(Intent.ACTION_POWER_CONNECTED)
            it.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            it.addAction("android.provider.Telephony.SMS_RECEIVED")
            it.addAction(ACTION_MY_BROADCAST)
            registerReceiver(broadcastReceiver, it)
        }

    }

    override fun onStart() {
        super.onStart()
        startBroadcastReceiver()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }



    inner class MyBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                Intent.ACTION_POWER_CONNECTED -> { showBroadcast(Intent.ACTION_POWER_CONNECTED) }
                ACTION_MY_BROADCAST -> { showBroadcast(ACTION_MY_BROADCAST) }
                else -> { showBroadcast(intent?.action ?: "NO ACTION")}
            }
        }

        private fun showBroadcast(msg: String) {
            println(msg)
            Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val ACTION_MY_BROADCAST = "ACTION_MY_BROADCAST"
    }
}