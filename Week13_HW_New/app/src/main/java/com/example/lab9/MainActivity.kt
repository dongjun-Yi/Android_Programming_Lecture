package com.example.lab9

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.lab9.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy{ ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonGet.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            /*intent.also {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    startForegroundService(it)
                else
                    startService(it)
            }*/

            binding.textView.text="${myService?.tickCount}"
        }
    }

    //접속한 서비스 객체
    private var myService: MyService?=null

    //서비스 접속을 관리하는 객체
    //p0는 서비스의이름, p1은 IBinder 객체
    private val serviceConnection = object : ServiceConnection {
        //서비스 접속에 성공했을 때
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            //MyService에서 LocalBinder 내부 클래스에서 getService()라는 메서드를 사용하여 서비스 객체를 가져온다.
            myService = (p1 as MyService.LocalBinder).getService()
        }
        //서비스 접속을 해체했을 때
        override fun onServiceDisconnected(p0: ComponentName?) {
            myService= null
        }
    }

    //1. 서비스에 접속했을 때 MyService에서 onBind 메서드가 자동 호출된다.
    //2. onBind에서 반환하는 binder 객체를 OS에서 받는다.
    //3. OS는 서비스 접속에 성공해 onServiceConnected메서드가 자동 호출될때 p1에 biner객체를 넘겨준다.
    //4. 그 후 binder객체는 IBinder형으로 넘어와서 LocalBinder로 형변환을 시켜준다.
    //5. 그 후 LocalBinder 내부 클래스에 있는 getService()메서드를 활용해 서비스객체를 받아온다

    override fun onStart() {
        super.onStart()
        Intent(this,MyService::class.java).also {
            //서비스에 접속한다.
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

