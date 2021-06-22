package com.example.myapplicationviewmodel.repository

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.example.myapplicationviewmodel.contacts.Contact

class ContactsRepositoryImpl : ContactsRepository {
    override fun getAllHistory(context: Context): List<Contact> {
        context.let {
            val listContacts = mutableListOf<Contact>()
            val contentResolver: ContentResolver = it.contentResolver
            val cursorWithContacts: Cursor? = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME
            )

            cursorWithContacts?.let { cursor ->
                for (i in 0..cursor.count) {
                    if (cursor.moveToPosition(i)) {
                        val name =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        val id =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                        val phones =
                            contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                null,
                                null)
                        var phoneNumber = ""
                        while (phones!!.moveToNext()) {
                            phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        }
                        phones.close()
                        listContacts.add(Contact(i, name, phoneNumber))
                    }
                }
                return listContacts
            }
            cursorWithContacts?.close()
        }
        return mutableListOf()
    }

}