package com.gmail.devpelegrino.cronoagua.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gmail.devpelegrino.cronoagua.R
import com.gmail.devpelegrino.cronoagua.database.getDatabase
import com.gmail.devpelegrino.cronoagua.repository.UserProfileRepository
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toolbar : Toolbar
    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var drawer : DrawerLayout
    private lateinit var navController : NavController

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)
        startAppUserFragment()
        setupActionBar()
        setupNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.item_user -> {
            navController.navigate(R.id.action_waterManagerFragment_to_userProfileFragment)
            true
        }

        R.id.item_config -> {
            navController.navigate(R.id.action_waterManagerFragment_to_configurationFragment)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.item_user -> {
//                val toolBar = supportActionBar
//                toolBar!!.title = getString(R.string.menu_title_user_profile)
//                navController.navigate(R.id.action_waterManagerFragment_to_userProfileFragment)
//            }
//            R.id.item_config -> {
//                Log.i("Teste", "entrei item_config")
//                val toolBar = supportActionBar
//                toolBar!!.title = getString(R.string.menu_title_config)
//                navController.navigate(R.id.action_waterManagerFragment_to_configurationFragment)
//            }
//        }
        return false
    }

    override fun onSupportNavigateUp()
            = navigateUp(findNavController(R.id.nav_host_fragment), drawer)


    @InternalCoroutinesApi
    fun startAppUserFragment() {
        runBlocking {
            val database = getDatabase(application)
            val usersRepository = UserProfileRepository(database)
            if (usersRepository.getAllUsers().isEmpty()) {
                navController.navigate(R.id.action_waterManagerFragment_to_userProfileFragment)
            }
        }
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.nav_host_fragment)
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.bringToFront()
        setupActionBarWithNavController(navController, drawer)
        navigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            val toolBar = supportActionBar ?: return@addOnDestinationChangedListener
            when(destination.id) {
                R.id.home -> {
                    toolBar.title = getString(R.string.app_name)
                }
                R.id.item_user -> {
                    Log.i("Teste", "entrei item_user")
                    toolBar.title = getString(R.string.menu_title_user_profile)
                    navController.navigate(R.id.action_waterManagerFragment_to_userProfileFragment)
                }
                R.id.item_config -> {
                    Log.i("Teste", "entrei item_config")
                    toolBar.title = getString(R.string.menu_title_config)
                    navController.navigate(R.id.action_waterManagerFragment_to_configurationFragment)
                }
                else -> {
                    toolBar.title = getString(R.string.app_name)
                }
            }
        }
    }

    private fun setupActionBar() {
        drawer = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }
}