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
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
//                R.id.nav_tools, R.id.nav_share, R.id.nav_send
//            ), drawerLayout
//        )

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

       //    window.decorView.systemUiVisibility = // Tells the system that the window wishes the content to
//                // be laid out at the most extreme scenario. See the docs for
//                // more information on the specifics
//            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
//                    // Tells the system that the window wishes the content to
//                    // be laid out as if the navigation bar was hidden
//                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//


        //no title
        supportActionBar?.setDisplayShowTitleEnabled(false)


    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        //return NavigationUI.navigateUp(navController, appBarConfiguration)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}
