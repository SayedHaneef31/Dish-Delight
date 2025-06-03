package com.example.dishdelight

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LocaleHelper
{
    private val PREFS_NAME = "prefs"
    private val KEY_LANG = "app_lang"

    fun getLanguage(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LANG, Locale.getDefault().language) ?: "en"
    }

    fun setLanguage(context: Context, language: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_LANG, language).apply()
    }

    fun updateLocale(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }
}