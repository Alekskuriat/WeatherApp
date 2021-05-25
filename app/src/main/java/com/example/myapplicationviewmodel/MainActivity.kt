package com.example.myapplicationviewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationviewmodel.router.AppRouter
import com.example.myapplicationviewmodel.router.RouterHolder
import com.example.myapplicationviewmodel.ui.main.MainFragment


class MainActivity : AppCompatActivity(), RouterHolder {

    private lateinit var router: AppRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        router = AppRouter(supportFragmentManager)
        if (savedInstanceState == null) {
            router.showStart()
        }
    }

    override fun getRouter(): AppRouter {
        return router
    }
}