package com.github.petrchatrny.puzzle8.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.github.petrchatrny.puzzle8.R
import com.github.petrchatrny.puzzle8.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // setup navigation
        binding.mainBottomNav.setupWithNavController(findNavController(R.id.navHostFragment))
    }
}