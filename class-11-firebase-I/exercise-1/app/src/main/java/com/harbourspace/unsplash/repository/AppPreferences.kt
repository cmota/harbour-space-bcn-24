package com.harbourspace.unsplash.repository

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

private const val KEY_DARK_THEME = "key_dark_theme"

class AppPreferences(activity: Activity) {

    private val preferences = activity.getSharedPreferences("unsplash", Context.MODE_PRIVATE)

    fun setForceDarkTheme(value: Boolean) {
        with (preferences.edit()) {
            putBoolean(KEY_DARK_THEME, value)
            apply()
        }
    }

    fun isDarkTheme(): Boolean {
        val isSystemDarkTheme = AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO
        return preferences.getBoolean(KEY_DARK_THEME, isSystemDarkTheme)
    }
}