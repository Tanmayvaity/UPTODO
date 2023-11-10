package com.example.uptodo

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.uptodo.databinding.ActivityMainBinding
import com.example.uptodo.fragments.HomeFragment
import com.example.uptodo.fragments.NotesFragment
import com.example.uptodo.fragments.PlannerFragment
import com.example.uptodo.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding.mainBottomNavigation.background = null
        binding.mainBottomNavigation.menu.getItem(2).isEnabled =false


        binding.mainBottomNavigation.setOnItemSelectedListener {
            navigationLogic(it)
            true
        }

        binding.mainBottomNavigation.selectedItemId = R.id.home_screen
    }

    private fun navigationLogic(item: MenuItem) {
        lateinit var fragment: Fragment
        when (item.itemId) {
            R.id.home_screen -> {
                fragment = HomeFragment()
            }

            R.id.planner_screen -> {
                fragment = PlannerFragment()
            }

            R.id.notes_screen -> {
                fragment = NotesFragment()
            }

            R.id.settings_screen -> {
                fragment = SettingsFragment()
            }
        }
        supportFragmentManager.beginTransaction().replace(
            R.id.fl_container, fragment
        ).commit()
    }
}