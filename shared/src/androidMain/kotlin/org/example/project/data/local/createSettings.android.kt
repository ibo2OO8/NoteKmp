package org.example.project.data.local

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

private lateinit var appContext: Context


fun initSettings(context: Context) {
    appContext = context.applicationContext
}


actual fun createSettings(): Settings {

    val prefs = appContext.getSharedPreferences(
        "notes_prefs",
        Context.MODE_PRIVATE
    )

    return SharedPreferencesSettings(prefs)
}