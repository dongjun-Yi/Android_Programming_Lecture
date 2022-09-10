package com.example.week10_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.week10_practice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appbarc : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nhf =supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment

        appbarc = AppBarConfiguration(nhf.navController.graph)
        setupActionBarWithNavController(nhf.navController,appbarc)

        binding.bottomNavigationView.setupWithNavController(nhf.navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragmentContainerView2).navigateUp(appbarc) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.AlertDialog ->MyDialog().show(supportFragmentManager, "")
            R.id.DateTimepicker ->DateDialog().show(supportFragmentManager, "")
            R.id.Page1Fragment -> item.onNavDestinationSelected(findNavController(R.id.fragmentContainerView2))
            R.id.Page2Fragment -> item.onNavDestinationSelected(findNavController(R.id.fragmentContainerView2))
        }
        return true
    }
}