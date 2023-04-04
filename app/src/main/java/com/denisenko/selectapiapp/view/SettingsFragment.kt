package com.denisenko.selectapiapp.view

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.denisenko.selectapiapp.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}