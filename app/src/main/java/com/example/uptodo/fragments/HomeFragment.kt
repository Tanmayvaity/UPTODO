package com.example.uptodo.fragments

import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.uptodo.R
import com.example.uptodo.activities.LoginActivity
import com.example.uptodo.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private var colors : MutableList<Int>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.trailTv.setGradientColor(
            ContextCompat.getColor(view.context, R.color.greenish),
            ContextCompat.getColor(view.context,R.color.purple)
        )


        binding.trailTv.setOnClickListener{

            Intent(view.context,LoginActivity::class.java).apply{
                startActivity(this)
            }

        }




    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun TextView.setGradientColor(vararg color : Int){
        val textPaint = this.paint
        val width = textPaint.measureText(this.text.toString())
        val startColor = color[0]
        val endColor = color[1]
        val shader = LinearGradient(
            0f, 0f, width, this.textSize,startColor,endColor,Shader.TileMode.CLAMP
        )

        this.paint.shader = shader
        this.setTextColor(startColor)

    }



}