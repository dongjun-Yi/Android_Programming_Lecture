package com.example.week12_codeex

import android.content.Context
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.io.File
import java.lang.IllegalArgumentException
import java.lang.reflect.Array.set

//Context는 애플리케이션의 현재 상태를 나타내고, 액티비티와 어플리케이션의 정보를 얻기 위해 사용할 수 있다.
//Conext는 두종류 - Application Context, Activity Conext
//둘 중 가까운 것을 선택해 사용한다.

class MyViewModel(context : Context) : ViewModel(){
    private val fileInternal =File(context.filesDir, "appfile.txt")
    private val fileExternal=
        if(isExternalStorageMounted)
            File(context.getExternalFilesDir(null), "appfile.txt")
        else
            fileInternal

    var valueInternal : String = readValue(fileInternal)
        set(v) {
            field=v
            writeValue(fileInternal,v)
        }
    var valueExternal : String = readValue(fileInternal)
        set(v) {
            field=v
            writeValue(fileInternal,v)
        }


    private fun readValue(file:File): String{
        return try {
            println("$file")
            file.readText(Charsets.UTF_8)
        }catch (e : Exception){
            ""
        }
    }

    private fun writeValue(file:File, value:String){
       file.writeText(value, Charsets.UTF_8)
    }


    private val isExternalStorageMounted: Boolean
        get() {
            //외부 데이터와 mount가 성공하면 MEDIA_MOUNTED가 대입되서 true를 return
            val state =Environment.getExternalStorageState()
            return state==Environment.MEDIA_MOUNTED
        }

    //ViewModel의 생성자 인자로 받기 위해 커스텀으로 ViewModelProvider.Factory로 커스터마이징
    //modelClass는 MyViewModel::class.java와 같은지 검사하고 같다면 MyViewModel 객체를 return


    class MyViewModelFactory(private val context : Context) : ViewModelProvider.Factory{
        //modelclass는
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
           return if(modelClass.isAssignableFrom(MyViewModel::class.java))
               return MyViewModel(context) as T
           else
               throw IllegalArgumentException()
        }
    }

}