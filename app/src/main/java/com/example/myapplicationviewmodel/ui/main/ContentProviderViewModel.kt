package com.example.myapplicationviewmodel.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplicationviewmodel.appState.AppStateContacts
import com.example.myapplicationviewmodel.repository.ContactsRepository
import com.example.myapplicationviewmodel.repository.ContactsRepositoryImpl


class ContentProviderViewModel(
    val contactLiveData: MutableLiveData<AppStateContacts> = MutableLiveData(),
    private val contactsRepository: ContactsRepository = ContactsRepositoryImpl()
) : ViewModel() {

    fun getListContacts(context: Context) {
        contactLiveData.value = AppStateContacts.Loading
        Thread{
            contactLiveData.postValue(AppStateContacts.Success(contactsRepository.getAllHistory(context)))
        }.start()
    }

}
