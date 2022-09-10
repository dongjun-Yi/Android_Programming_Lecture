package com.example.week10_lec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.week10_lec.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var appbarc : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.setOnClickListener {
            showPopup(it)
        }

        val nhf = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        appbarc = AppBarConfiguration(nhf.navController.graph) //topdestination을 정해주지 않으면 기본으로 home이 top이됨
        setupActionBarWithNavController(nhf.navController, appbarc) //앱바와 navcontroller 연결

        binding.bottomNavigationView.setupWithNavController(nhf.navController) //바텀메뉴와 navcontroller 연결
    }

    override fun onSupportNavigateUp(): Boolean { //뒤로가기버튼(navigateup) 생성하는 코드
        return findNavController(R.id.fragmentContainerView).navigateUp(appbarc) || super.onSupportNavigateUp()
    }

    private fun showPopup(v: View){
        PopupMenu(this, v).apply {
            inflate(R.menu.menu)
            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.Item1 -> Snackbar.make(v, "Item1", Snackbar.LENGTH_SHORT).show()
                }
                true
            }
        }.show()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.Item1 ->MyDialogFragment().show(supportFragmentManager, "")
            R.id.page1Fragment -> item.onNavDestinationSelected(findNavController(R.id.fragmentContainerView))
            R.id.Item3 -> startActivity(Intent(this, NavDrawerActivity::class.java))
            else->return super.onOptionsItemSelected(item)
        }
        return true
    }
}