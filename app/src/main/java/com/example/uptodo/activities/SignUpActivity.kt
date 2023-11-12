package com.example.uptodo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.example.uptodo.R
import com.example.uptodo.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.tbAuth)
//        supportActionBar?.setDisplayShowTitleEnabled(false)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setSpanTextColor()
    }

    private fun setSpanTextColor(){
        val text = binding.tvLoginQuestion.text
        val spannable = SpannableString(text.toString())
        val clickableSpan = object : ClickableSpan(){
            override fun onClick(view: View) {
                Intent(this@SignUpActivity,LoginActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }
            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = ContextCompat.getColor(this@SignUpActivity,R.color.mariner)
                textPaint.isUnderlineText = false
            }

        }
        spannable.setSpan(clickableSpan,25,text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvLoginQuestion.text = spannable
        binding.tvLoginQuestion.movementMethod = LinkMovementMethod.getInstance()

    }
}