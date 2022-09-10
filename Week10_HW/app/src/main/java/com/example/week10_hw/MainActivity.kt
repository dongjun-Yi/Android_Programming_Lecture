package com.example.week10_hw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.week10_hw.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var appbarc : AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //먼저 NavHostFagment를 찾는다.
        //oncreate에서 findNavController는 오류가 있기 때문에 view를 찾아서 navcontroller을 알아내야함
        //findNavController.navigate()를 사용함
        val nhf =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        //topdest를 설정한 fragment는 up버튼이 안나오게 해줌
        val topDest = setOf(R.id.homeFragment, R.id.page1Fragment, R.id.page2Fragment)
        
        //appbarconfiguration으로 topdest와 drawerlayout을 연결
        appbarc = AppBarConfiguration(topDest, binding.drawerLayout)
        //drawerlayout에 navcontroller연결
        setupActionBarWithNavController(nhf.navController, appbarc)

        binding.navigationView.setNavigationItemSelectedListener {
            binding.drawerLayout.close()
            when (it.itemId) {
                R.id.myDialogFragment -> {
                    MyDialogFragment().show(supportFragmentManager, "")
                    true
                }
                else -> { // 기타 다른 항목들에 대해서는 같은 ID의 네비게이션 Destination으로 이동하도록
                    it.onNavDestinationSelected(nhf.navController)
                }
            }
        }

    }

    //up버튼 등록하는 메서드(<-)
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragmentContainerView).navigateUp(appbarc) || super.onSupportNavigateUp()
    }


}
