package com.example.uptodo.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.uptodo.R
import com.example.uptodo.Utils.Validation
import com.example.uptodo.databinding.ActivitySignUpBinding
import com.google.android.play.integrity.internal.c

class SignUpActivity : AppCompatActivity() {
    companion object {
        const val TAG = "SignUpActivity"
    }


    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSpanTextColor()




        binding.etUsernameSignUp.addTextChangedListener(getTextWatcher(binding.etUsernameSignUp))
        binding.etEmailSignUp.addTextChangedListener(getTextWatcher(binding.etEmailSignUp))
        binding.etPasswordSignUp.addTextChangedListener(getTextWatcher(binding.etPasswordSignUp))
        binding.etConfirmPasswordSignUp.addTextChangedListener(getTextWatcher(binding.etConfirmPasswordSignUp))

        binding.btnSignUp.setOnClickListener{
            validateUser(binding.etUsernameSignUp.text.toString())
            validateEmail(binding.etEmailSignUp.text.toString())
            validatePassword(binding.etPasswordSignUp.text.toString())
            validateConfirmPassword(binding.etConfirmPasswordSignUp.text.toString(),binding.etPasswordSignUp.text.toString())
        }




    }







    private fun validateUser(text:String){
        Validation().validateUsername(
            username = text,
            tl = binding.tilUsernameSignUp
        )
    }

    private fun validateEmail(email:String){
        Validation().validateEmail(
            email= email,
            tl = binding.tilEmailSignUp
        )
    }

    private fun validatePassword(password:String){
        Validation().validatePassword(
            password = password,
            tl = binding.tilPassswordSignUp
        )
    }

    private fun validateConfirmPassword(password: String,confirmPassword:String){
        Validation().validateConfirmPassword(
            password = password,
            confirmPassword  = confirmPassword,
            tl = binding.tilConfirmPassswordSignUp
        )
    }






    private fun setSpanTextColor() {
        val text = binding.tvLoginQuestion.text
        val spannable = SpannableString(text.toString())
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Intent(this@SignUpActivity, LoginActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }

            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = ContextCompat.getColor(this@SignUpActivity, R.color.mariner)
                textPaint.isUnderlineText = false
            }

        }
        spannable.setSpan(clickableSpan, 25, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvLoginQuestion.text = spannable
        binding.tvLoginQuestion.movementMethod = LinkMovementMethod.getInstance()

    }

    private fun getTextWatcher(editText: EditText):TextWatcher  {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {


            }
            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {

                if(editText.id == R.id.et_username_sign_up){
                    validateUser(text.toString())
                }
                if(editText.id == R.id.et_email_sign_up){
                    validateEmail(text.toString())
                }

                if(editText.id == R.id.et_password_sign_up){
                    validatePassword(text.toString())
                }

                if(editText.id == R.id.et_confirm_password_sign_up){
                    validateConfirmPassword(text.toString(),binding.etPasswordSignUp.text.toString())
                }
            }

            override fun afterTextChanged(s: Editable) {

                if(editText.id == R.id.et_username_sign_up){
                    validateUser(s.toString())
                }
                if(editText.id == R.id.et_email_sign_up){
                    validateEmail(s.toString())
                }

                if(editText.id == R.id.et_password_sign_up){
                    validatePassword(s.toString())
                }

                if(editText.id == R.id.et_confirm_password_sign_up){
                    validateConfirmPassword(s.toString(),binding.etPasswordSignUp.text.toString())
                }
            }
        }
    }
}





