package com.gmail.devpelegrino.cronoagua.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gmail.devpelegrino.cronoagua.R
import com.gmail.devpelegrino.cronoagua.util.AppStartManagement
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.InternalCoroutinesApi


class MainActivity : AppCompatActivity() {
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        AppStartManagement.startApp(application, supportFragmentManager)
        setupNavigation()
//        setSupportActionBar(findViewById(R.id.toolbar_home))
    }

    override fun onSupportNavigateUp()
            = navigateUp(findNavController(R.id.nav_host_fragment),  findViewById<DrawerLayout>(R.id.drawer_layout))

    private fun setupNavigation() {
        // first find the nav controller
        val navController = findNavController(R.id.nav_host_fragment)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)

        setSupportActionBar(findViewById(R.id.toolbar_home))

        // then setup the action bar, tell it about the DrawerLayout
        setupActionBarWithNavController(this, navController)


        // finally setup the left drawer (called a NavigationView)
        navigationView.setupWithNavController(navController)

//        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
//            val toolBar = supportActionBar ?: return@addOnDestinationChangedListener
//            when(destination.id) {
//                R.id.home -> {
//                    toolBar.setDisplayShowTitleEnabled(false)
//                    binding.heroImage.visibility = View.VISIBLE
//                }
//                else -> {
//                    toolBar.setDisplayShowTitleEnabled(true)
//                    binding.heroImage.visibility = View.GONE
//                }
//            }
//        }
    }
}