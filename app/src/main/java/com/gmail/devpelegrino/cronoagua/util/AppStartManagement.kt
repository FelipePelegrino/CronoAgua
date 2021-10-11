package com.gmail.devpelegrino.cronoagua.util

import android.app.Application
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import com.gmail.devpelegrino.cronoagua.R
import com.gmail.devpelegrino.cronoagua.database.getDatabase
import com.gmail.devpelegrino.cronoagua.repository.UserProfileRepository
import kotlinx.coroutines.*

object AppStartManagement {

    @InternalCoroutinesApi
    fun startApp(application: Application, fragmentManager: FragmentManager) {
        val navHostFragment =
            fragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appStartJob = SupervisorJob()
        val appScope = CoroutineScope(appStartJob + Dispatchers.Main)
        appScope.launch {
            val database = getDatabase(application)
            val usersRepository = UserProfileRepository(database)
            if (usersRepository.getAllUsers().isEmpty()) {
                navController.setGraph(R.navigation.nav_graph_initial_user)
            } else {
                navController.setGraph(R.navigation.nav_graph_initial_water)
            }
        }
    }
}