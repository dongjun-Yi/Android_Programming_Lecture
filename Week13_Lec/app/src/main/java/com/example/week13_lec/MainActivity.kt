package com.example.week13_lec

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.week13_lec.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy{ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonService.setOnClickListener {
            Intent(this,MyService::class.java).also {
                if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O)
                    startForegroundService(it)
                else
                    startService(it)
            }
        }
    }

    private var myService:MyService?=null

    private val serviceConnection = object : ServiceConnection{
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            myService = (p1 as MyService.LocalBinder).getService()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            myService= null
        }

    }



    override fun onStart() {
        super.onStart()
        Intent(this,MyService::class.java).also {
            bindService(it,serviceConnection, BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        Intent(this,MyService::class.java).also {
            unbindService(serviceConnection)
        }
    }
}