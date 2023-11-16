package com.example.uptodo

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
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
    private var isFabRotated = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding.mainBottomNavigation.background = null
        binding.mainBottomNavigation.menu.getItem(2).isEnabled =false


        binding.mainBottomNavigation.setOnItemSelectedListener {
            navigationLogic(it)
            true
        }

        binding.fbAdd.setOnClickListener{
            fabAnimationLogic(it)

        }


        binding.mainBottomNavigation.selectedItemId = R.id.home_screen
    }

    private fun fabAnimationLogic(view:View){

        if(isFabRotated){
            view.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.fab_rotate_close
                )
            )


            binding.btnAddNotes.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.to_bottom
                )
            )

            binding.btnAddTask.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.to_bottom
                )
            )
            binding.btnAddHabit.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.to_bottom
                )
            )
            binding.btnAddGoal.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.to_bottom
                )
            )
            binding.btnAddNotes.visibility = View.GONE
            binding.btnAddTask.visibility = View.GONE
            binding.btnAddHabit.visibility = View.GONE
            binding.btnAddGoal.visibility = View.GONE

            isFabRotated = false
        }else{
            view.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.fab_rotate_open
                )
            )

            binding.btnAddNotes.visibility = View.VISIBLE
            binding.btnAddTask.visibility = View.VISIBLE
            binding.btnAddHabit.visibility = View.VISIBLE
            binding.btnAddGoal.visibility = View.VISIBLE

            binding.btnAddNotes.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.from_bottom
                )
            )

            binding.btnAddTask.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.from_bottom
                )
            )
            binding.btnAddHabit.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.from_bottom
                )
            )
            binding.btnAddGoal.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.from_bottom
                )
            )
            isFabRotated = true
        }
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
            R.id.main_container, fragment
        ).commit()
    }
}