package com.example.myapplicationviewmodel

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myapplicationviewmodel.router.AppRouter
import com.example.myapplicationviewmodel.router.RouterHolder
import com.example.myapplicationviewmodel.ui.main.HistoryFragment
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), RouterHolder {

    private lateinit var router: AppRouter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        router = AppRouter(supportFragmentManager)

        initNavigationDrawer()

        if (savedInstanceState == null) {
            router.showStart()
        }
    }

    override fun getRouter(): AppRouter {
        return router
    }

    private fun initNavigationDrawer() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        @SuppressLint("ResourceType") val actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.nav_city_select) {
                router.showCitySelect()
                drawer.closeDrawer(Gravity.LEFT)
            }
            if (item.itemId == R.id.nav_setting) {
                router.showSettings()
                drawer.closeDrawer(Gravity.LEFT)
            }
            if (item.itemId == R.id.nav_history) {
                router.showHistory()
                drawer.closeDrawer(Gravity.LEFT)
            }
            if (item.itemId == R.id.nav_contact) {
                router.showContacts()
                drawer.closeDrawer(Gravity.LEFT)
            }
            if (item.itemId == R.id.nav_map) {
                router.showMap()
                drawer.closeDrawer(Gravity.LEFT)
            }
            if (item.itemId == R.id.nav_about) {
                router.showAbout()
                drawer.closeDrawer(Gravity.LEFT)
            }
            return@setNavigationItemSelectedListener false
        }
    }
}