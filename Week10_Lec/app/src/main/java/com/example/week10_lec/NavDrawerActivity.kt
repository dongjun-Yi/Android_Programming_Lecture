package com.example.week10_lec

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.week10_lec.databinding.ActivityNavDrawerBinding

class NavDrawerActivity : AppCompatActivity() {
    private lateinit var appbarc : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNavDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nhf = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment

        val topDest =setOf(R.id.homeFragment,R.id.page1Fragment, R.id.page2Fragment)

        appbarc = AppBarConfiguration(topDest, binding.drawerLayout) //TopDest가 top
        setupActionBarWithNavController(nhf.navController, appbarc) //appbar 와 navigation 연결

        binding.NavigationView.setupWithNavController(nhf.navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragmentContainerView2).navigateUp(appbarc) || super.onSupportNavigateUp()
    }
}