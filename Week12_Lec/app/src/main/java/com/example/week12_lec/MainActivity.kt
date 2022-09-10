package com.example.week12_lec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.preference.PreferenceViewHolder
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById<TextView>(R.id.TextView)
        tv.setOnClickListener {
            startActivity(Intent(this,SettingsActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        val tv = findViewById<TextView>(R.id.TextView)
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val signature = pref.getString("signature", "")
        Snackbar.make(this, tv, "signature : ${signature}", Snackbar.LENGTH_SHORT).show()
    }
}