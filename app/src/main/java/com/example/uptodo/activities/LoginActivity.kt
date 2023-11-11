package com.example.uptodo.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.uptodo.R
import com.example.uptodo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbAuth)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setSpanTextColor()

    }
    private fun setSpanTextColor(){
        val text = binding.tvSignUpQuestion.text
        val spannable = SpannableString(text.toString())
        val clickableSpan = object : ClickableSpan(){
            override fun onClick(view: View) {
                Intent(this@LoginActivity,SignUpActivity::class.java).apply {
                    startActivity(this)
                }

            }

            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = ContextCompat.getColor(this@LoginActivity,R.color.mariner)
                textPaint.isUnderlineText = false
            }

        }
        spannable.setSpan(clickableSpan,23,text.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvSignUpQuestion.setText(spannable)
        binding.tvSignUpQuestion.movementMethod = LinkMovementMethod.getInstance()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}