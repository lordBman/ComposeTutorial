package com.bsoft.composetutorial.services

import android.app.Activity
import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.provider.ContactsContract
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class ActivityThread(private val activity: Activity) : Thread() {
    override fun run() {
        super.run();
        activity.runOnUiThread {

        }
    }
}

class LoginWorker(context: Context, parameters: WorkerParameters) : Worker(context, parameters){
    override fun doWork(): Result {
        val data = Data.Builder().putString("name", "bobby").build();

        return Result.success(data)
    }
}

class CustomObserver(private val context: Context) : ContentObserver(null) {
    init {
        context.contentResolver.registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true, this);
    }

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        val phoneCursor: Cursor? = context.contentResolver.query(ContactsContract.Contacts.CONTENT_URI, arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER), "", null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

        val phoneNumbers: ArrayList<String> = arrayListOf();
        while (phoneCursor?.moveToNext() == true){
            val phoneNumber: String? = phoneCursor.getString(phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
            if(phoneNumber != null && phoneNumber.first() == '0'){
                phoneNumbers.add(phoneNumber)
            }
        }
        phoneCursor?.close()
    }
}