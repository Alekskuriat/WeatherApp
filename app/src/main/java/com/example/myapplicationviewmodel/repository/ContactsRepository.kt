package com.example.myapplicationviewmodel.repository

import android.content.Context
import com.example.myapplicationviewmodel.contacts.Contact
import com.example.myapplicationviewmodel.data.Weather

interface ContactsRepository {
    fun getAllHistory(context: Context): List<Contact>
}