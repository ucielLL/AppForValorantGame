package com.example.team_val.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.team_val.R
import com.example.team_val.databinding.ActivityMainBinding
import com.example.team_val.ui.view_model.MainVieModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.team_val.ui.fragments_botton_menu.MainFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import org.checkerframework.common.returnsreceiver.qual.This
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainVieModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainVieModel::class.java)
        NavigationUI.setupWithNavController(
            binding.bottomNav,
            findNavController(R.id.nav_host_main)
        )
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_main)
                navHost?.let { navFragment ->
                    navFragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->
                        if (fragment is MainFragment ) {
                            finishAffinity()
                        } else {

                           fragment.findNavController().popBackStack()
                        }
                    }
                }
            }
        }
        )
        setupNav()

    }


    private fun setupNav() {
        val navController = findNavController(R.id.nav_host_main)
        binding.bottomNav.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mapsFragment -> showBottomNav()
                R.id.agentsFragment -> showBottomNav()
                R.id.lineupsFragment -> showBottomNav()
                R.id.mainFragment -> showBottomNav()
                else -> hideBottomNav()
            }
        }
    }

    private fun showBottomNav() {
       binding.bottomNav.visibility = View.VISIBLE

    }
    private fun hideBottomNav() {
        binding.bottomNav.visibility = View.GONE
    }




}

