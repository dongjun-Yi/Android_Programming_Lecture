package com.example.week12_hw

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat.getFont
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById<TextView>(R.id.textView)
        tv.setOnClickListener {
            startActivity(Intent(this,SettingsActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        val tv = findViewById<TextView>(R.id.textView)
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val getText = pref.getString("Name", "Hello World")
        val getSize = pref.getString("Size", "") ?: 100
        val getFont = pref.getBoolean("Italic", false)
        println("getText : $getText")
        println("getSize : $getSize")
        println("getFont : $getFont")

        tv.text = getText

        if(getSize.toString() == "small")
            tv.textSize = 10F
        else if(getSize.toString() == "medium")
            tv.textSize = 14F
        else
            tv.textSize = 20F

        if(getFont)
            tv.setTypeface(null, Typeface.ITALIC)
        else
            tv.setTypeface(null, Typeface.NORMAL)

    }
}

