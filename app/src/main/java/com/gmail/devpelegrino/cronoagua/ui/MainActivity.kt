package com.gmail.devpelegrino.cronoagua.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

    private lateinit var toolbar: Toolbar
    private lateinit var drawer: DrawerLayout
    private lateinit var navController: NavController

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        navigationHandling(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp() =
        navigateUp(findNavController(R.id.nav_host_fragment), drawer)


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
            navigationHandling(destination.id)
        }

        navigationView.setNavigationItemSelectedListener {
            navigationHandling(it.itemId)
            return@setNavigationItemSelectedListener true
        };
    }

    private fun navigationHandling(idItem: Int) {
        val toolBar = supportActionBar
        if (toolbar != null && navController != null) {
            when (idItem) {
                R.id.home -> {
                    toolbar.menu.setGroupVisible(R.id.group_menu, true)
                    toolBar!!.title = getString(R.string.app_name)
                }
                R.id.item_user -> {
                    toolbar.menu.setGroupVisible(R.id.group_menu, false)
                    toolBar!!.title = getString(R.string.menu_title_user_profile)
                    navController.navigate(R.id.action_waterManagerFragment_to_userProfileFragment)
                }
                R.id.item_config -> {
                    toolbar.menu.setGroupVisible(R.id.group_menu, false)
                    toolBar!!.title = getString(R.string.menu_title_config)
                    navController.navigate(R.id.action_waterManagerFragment_to_configurationFragment)
                }
                else -> {
                    toolBar!!.title = getString(R.string.app_name)
                }
            }
            drawer.closeDrawers()
        }
    }

    private fun setupActionBar() {
        drawer = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }
}