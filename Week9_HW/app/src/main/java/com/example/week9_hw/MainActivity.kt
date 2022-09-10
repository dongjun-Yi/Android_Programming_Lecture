package com.example.week9_hw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.week9_hw.databinding.HomeFragmentBinding
import com.example.week9_hw.databinding.Nav1FragmentBinding
import com.example.week9_hw.databinding.Nav2FragmentBinding

class home_fragment : Fragment(R.layout.home_fragment){
    private val viewModel : MyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = HomeFragmentBinding.bind(view)
        binding.button.setOnClickListener {
            viewModel.increase()
            findNavController().navigate(R.id.action_home_fragment_to_nav_fragment1)
        }
        viewModel.testLiveData.observe(viewLifecycleOwner){
            binding.textView.text="$it"
        }

    }
}
class Nav_fragment1 : Fragment(R.layout.nav1_fragment){
    private val viewModel : MyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = Nav2FragmentBinding.bind(view)
        binding.button.setOnClickListener {
            viewModel.increase()
            findNavController().navigate(R.id.action_nav_fragment1_to_nav_fragment2)
        }
        viewModel.testLiveData.observe(viewLifecycleOwner){
            binding.textView.text="$it"
        }

    }
}
class Nav_fragment2 : Fragment(R.layout.nav2_fragment){
    private val viewModel : MyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = Nav1FragmentBinding.bind(view)
        binding.button.setOnClickListener {
            viewModel.increase()
            findNavController().navigate(R.id.action_nav_fragment2_to_home_fragment)
        }
        viewModel.testLiveData.observe(viewLifecycleOwner){
            binding.textView.text="$it"
        }

    }
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}