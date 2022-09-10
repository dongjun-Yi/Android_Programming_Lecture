package com.example.week14_contentresvole

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.week14_contentresvole.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    //전화로그에서 로그 가져오기
    private fun readCallLog() {
        if (!hasPermission(Manifest.permission.READ_CALL_LOG))
            return
        // query ( URI, projection, selection )
        // 테이블 컬럼 이름
        val projection = arrayOf(CallLog.Calls._ID, CallLog.Calls.NUMBER)
        //val selection =
        val cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, projection, null, null, null)
        val str = StringBuilder()
        cursor?.apply {
            val idxWord = getColumnIndex(CallLog.Calls.NUMBER)
            while (moveToNext()) {
                println(getString(idxWord))
                str.append(getString(idxWord))
                str.append("\n")
            }
            close()
        }
        binding.textViewCallLog.text=str
    }
    
    //외부 저장소에서 사진가져오기
    private fun readMedia() {
        if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE))
            return

        //외부 저장소 URI 구현
        //URI란 content provider에 접하려는 주소
        //최근버전은 get을 통해 uri를 가져오고
        //구버전은 기존 경로로 가져옴
        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val projection = arrayOf(MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.TITLE)
        
        //contentResolver객체를 통해 query메소드로 실행
        //켄텐트 uri를 사용해서 query를 함
        //query를 통해 검색된 결과를 return하므로 거기를 가리키는 cursor를 얻음
        //이를 통해 레코드에서 데이터를 찾듯이 moveToNext를 사용하여 데이터를 가져옴
        //projection은 우리가 select를 할때 컬럼 이름에 해당
        //selection은 where절에 해당
        //sortorder는 oderby절에 해당
        val cursor = contentResolver.query(collection, projection, null, null, null)
        cursor?.apply {
            val idCol = getColumnIndex(MediaStore.Images.ImageColumns._ID)
            val titleCol = getColumnIndex(MediaStore.Images.ImageColumns.TITLE)
            while (moveToNext()) {
                //withAppendId를 이용해서 외부저장소에 있는 content항목을 가르킨다.
                val contentUri = ContentUris.withAppendedId(
                    collection,
                    getLong(idCol)
                )
                val title = getString(titleCol)
                //withAppendId를 이요해서 contenturi를 알아낸것을 openInputStream을 통해 해당 항목을 decode한다.
                val bitmap = contentResolver.openInputStream(contentUri)?.use {
                    BitmapFactory.decodeStream(it)
                }
                binding.textViewImageTitle.text = title
                binding.imageView.setImageBitmap(bitmap)
                break // display the first image
            }
            close()
        }
    }

    private fun hasPermission(perm: String) =
        checkSelfPermission(perm) == PackageManager.PERMISSION_GRANTED

    //2개이상의 권한을 동시에 요청하려는 경우 다중 권한 다이얼로그로 해야함
    private fun requestMultiplePermission(perms: Array<String>) {
        val requestPerms = perms.filter { checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED }
        if (requestPerms.isEmpty())
            return

        //RequestMultiplePermissions()를 이용함
        val requestPermLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val noPerms = it.filter { item -> item.value == false }.keys
            if (noPerms.isNotEmpty()) { // there is a permission which is not granted!
                AlertDialog.Builder(this).apply {
                    setTitle("Warning")
                    setMessage(getString(R.string.no_permission, noPerms.toString()))
                }.show()
            }
        }
        val showRationalePerms = requestPerms.filter {shouldShowRequestPermissionRationale(it)}
        if (showRationalePerms.isNotEmpty()) {
            // you should explain the reason why this app needs the permission.
            AlertDialog.Builder(this).apply {
                setTitle("Reason")
                setMessage(getString(R.string.req_permission_reason, requestPerms))
                setPositiveButton("Allow") { _, _ -> requestPermLauncher.launch(requestPerms.toTypedArray()) }
                setNegativeButton("Deny") { _, _ -> }
            }.show()
        } else {
            // should be called in onCreate()
            requestPermLauncher.launch(requestPerms.toTypedArray())
        }
    }
}