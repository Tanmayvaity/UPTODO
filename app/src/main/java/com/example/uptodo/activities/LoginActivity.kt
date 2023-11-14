package com.example.uptodo.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.uptodo.R
import com.example.uptodo.Utils.Validation
import com.example.uptodo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.tbAuth)
//        supportActionBar?.setDisplayShowTitleEnabled(false)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setSpanTextColor()

        binding.etEmailLogin.addTextChangedListener(getTextWatcher(binding.etEmailLogin))
        binding.etPasswordLogin.addTextChangedListener(getTextWatcher(binding.etPasswordLogin))
        binding.btnLogin.setOnClickListener{
            validateEmail(binding.etEmailLogin.text.toString())
            validatePassword(binding.etEmailLogin.text.toString())
        }

    }

    private fun validateEmail(email:String){
        Validation().validateEmail(
            email= email,
            tl = binding.tilEmailLogin
        )
    }

    private fun validatePassword(password:String){
        if(password.isEmpty() || password.isBlank()){
            binding.tilPassswordLogin.error = "password cannot be empty"
            return
        }

        binding.tilPassswordLogin.error = null
    }




    private fun setSpanTextColor(){
        val text = binding.tvSignUpQuestion.text
        val spannable = SpannableString(text.toString())
        val clickableSpan = object : ClickableSpan(){
            override fun onClick(view: View) {
                Intent(this@LoginActivity,SignUpActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }

            }

            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = ContextCompat.getColor(this@LoginActivity,R.color.mariner)
                textPaint.isUnderlineText = false
            }

        }
        spannable.setSpan(clickableSpan,23,text.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvSignUpQuestion.text = spannable
        binding.tvSignUpQuestion.movementMethod = LinkMovementMethod.getInstance()

    }

    private fun getTextWatcher(editText: EditText):TextWatcher  {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {


            }
            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {


                if(editText.id == R.id.et_email_login){
                    validateEmail(text.toString())
                }

                if(editText.id == R.id.et_password_login){
                    validatePassword(text.toString())
                }


            }

            override fun afterTextChanged(s: Editable) {

                if(editText.id == R.id.et_email_login){
                    validateEmail(s.toString())
                }

                if(editText.id == R.id.et_password_login){
                    validatePassword(s.toString())
                }
            }
        }
    }
}