package com.example.week9_lec

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.week9_lec.databinding.ExampleFragBinding

class MyViewModel : ViewModel(){
    val testLiveData = MutableLiveData<Int>()
    init {
        testLiveData.value=0
    }

    fun increase() {
        testLiveData.value= (testLiveData.value ?: 0) +1
    }
}


class ExampleFragment : Fragment(R.layout.example_frag){
    private val viewModel : MyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = ExampleFragBinding.bind(view)
        binding.button.setOnClickListener {
            viewModel.increase()
        }

        binding.button2.setOnClickListener {
            findNavController().navigate(R.id.action_exampleFragment_to_example2Fragment)
        }
    }
}
class Example2Fragment : Fragment(R.layout.example_frag2)

class MainActivity : AppCompatActivity() {

    private val viewModel : MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment, Example2Fragment::class.java, null)
            addToBackStack(null)
        }*/

        viewModel.testLiveData.observe(this){
            title = "$it"
        }
    }
}