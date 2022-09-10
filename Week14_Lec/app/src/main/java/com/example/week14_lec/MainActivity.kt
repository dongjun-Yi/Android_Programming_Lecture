package com.example.week14_lec

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.example.week14_lec.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val broadcastReceiver = MyBroadcastReceiver()
    private val binding by lazy{ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /*if(checkSelfPermission(Manifest.permission.RECEIVE_SMS)==PackageManager.PERMISSION_DENIED){
            val launcher = registerForActivityResult(ActivityResultContracts .RequestPermission()){

            }
            launcher.launch(Manifest.permission.RECEIVE_SMS)
        }*/
        
        //외부저장소 접근이 디나이 됐다면 다이얼로그 띄우기
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
            val launcher = registerForActivityResult(ActivityResultContracts .RequestPermission()){

            }
            launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        //외부저장소에 파일을 가져오는 코드
        val collection =if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.Q)
            //파일의 주소를 가지고 외부저장소에 접근
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        else
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val project = arrayOf(MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.TITLE)
        val cursor =contentResolver.query(collection, project,null,null, null)
        cursor?.apply {
            val idCol = getColumnIndex(MediaStore.Images.ImageColumns._ID)
            val idTitle = getColumnIndex(MediaStore.Images.ImageColumns.TITLE)
            while(moveToNext()){
                val title = getString(idTitle)
                println(title)
            }
            close()
        }
    }
    //안드로이드 8.0이상부터는 코드로 broadcast를 등록해야함
    override fun onStart() {
        super.onStart()
        val ifilter = IntentFilter()
        ifilter.addAction(ACTION_MY_BROADCAST)
        ifilter.addAction("android.provider.Telephony.SMS_RECEIVED")
        registerReceiver(broadcastReceiver, ifilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    inner class MyBroadcastReceiver:BroadcastReceiver(){
        //broadcast 종류에 따라 분기
        override fun onReceive(p0: Context?, p1: Intent?) {
            when(p1?.action){
                ACTION_MY_BROADCAST ->{
                    binding.textView.text=ACTION_MY_BROADCAST
                }
                "android.provider.Telephony.SMS_RECEIVED"->{
                    binding.textView.text="Sms_RECEIVED"
                }
            }
        }
    }

    companion object{
        const val ACTION_MY_BROADCAST = "ACTION_MY_BROADCAST"
    }
}