package com.example.demoapplication

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.motion_drawerlayout)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

//        toolbar?.setTitleTextColor(
//            ContextCompat.getColor(
//                this,
//                 android.R.color.white
//            )
//        )


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            navController.graph,
            drawerLayout
        )

//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
//        NavigationUI.setupWithNavController(navView, navController)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //toolbar.visibility = View.GONE

        //This sets system navigation also transparent .. one way to deal is to add space view in app_bar_main
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        //no title
        window.statusBarColor = getColor(R.color.semiTransparent)
        supportActionBar?.setDisplayShowTitleEnabled(false)


    }

//    fun showProgressBar() {
//        toolbar_progress.visibility = View.VISIBLE
//    }
//
//    fun hideProgressBar() {
//        toolbar_progress.visibility = View.GONE
//    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        //return NavigationUI.navigateUp(navController, appBarConfiguration)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}
