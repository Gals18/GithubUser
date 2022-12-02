package com.galuhsaputri.githubusers.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.galuhsaputri.githubusers.R
import com.galuhsaputri.githubusers.databinding.ActivitySettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private val binding : ActivitySettingsBinding by lazy {
        ActivitySettingsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
    }

    private fun initToolbar() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.settings, MySettingsFragment())
            .commit()

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.settings)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}