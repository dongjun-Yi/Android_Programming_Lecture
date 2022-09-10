package com.example.week12_codeex

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.week12_codeex.databinding.ActivityMainBinding
import androidx.activity.viewModels

class ShowValueDialog(private val msg: String) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            setMessage(msg)
            setPositiveButton("Ok") { _, _ -> }
        }.create()
    }
}
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MyViewModel> { MyViewModel.MyViewModelFactory(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonWriteIn.setOnClickListener {
            viewModel.valueInternal = binding.textView.text.toString()
        }

        binding.buttonReadIn.setOnClickListener {
            ShowValueDialog(viewModel.valueInternal).show(supportFragmentManager, "ShowValueDialog")
        }

        binding.buttonWriteExt.setOnClickListener {
            viewModel.valueExternal = binding.textView.text.toString()

        }
        binding.buttonReadExt.setOnClickListener {
            ShowValueDialog(viewModel.valueExternal).show(supportFragmentManager, "ShowValueDialog")
        }
    }

    override fun onStart() {
        super.onStart()
        displaySettings()
    }

    private fun displaySettings() {
        val settings = PreferenceManager.getDefaultSharedPreferences(this)
        val signature = settings.getString("signature", "")
        val reply = settings.getString("reply", "")
        val sync = settings.getBoolean("sync", false)
        val attachment = settings.getBoolean("attachment", false)
        val str = """signature: $signature
reply: $reply
sync: $sync
attachment: $attachment
"""
        binding.textViewSettings.text=str
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}