package com.example.uptodo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uptodo.R
import com.example.uptodo.databinding.ActivityGoalBinding
import com.example.uptodo.fragments.EditGoalFragment

class GoalActivity : AppCompatActivity() {


    private lateinit var binding:ActivityGoalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val fragmentTag : String? = intent.getStringExtra("FRAGMENT_TAG")

        if(fragmentTag.equals("EDIT_FRAGMENT")){
            supportFragmentManager.beginTransaction().replace(
                R.id.fl_goal_container,EditGoalFragment(),fragmentTag
            ).commit()
        }

    }
}