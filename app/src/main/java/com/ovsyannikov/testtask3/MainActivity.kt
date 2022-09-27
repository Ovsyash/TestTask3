package com.ovsyannikov.testtask3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.ovsyannikov.testtask3.data.persons.Result
import com.ovsyannikov.testtask3.databinding.ActivityMainBinding
import com.ovsyannikov.testtask3.person.PersonFragment
import com.ovsyannikov.testtask3.person.PersonViewModel
import com.ovsyannikov.testtask3.persons.PersonsFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, PersonsFragment.newInstance())
                .commit()
        }
    }
}