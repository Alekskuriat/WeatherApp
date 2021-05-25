package com.example.myapplicationviewmodel.router


import androidx.fragment.app.FragmentManager
import com.example.myapplicationviewmodel.R
import com.example.myapplicationviewmodel.ui.main.MainFragment


class AppRouter(
    private val fragmentManager: FragmentManager
){


    fun showStart() {
        fragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commit()
    }


}