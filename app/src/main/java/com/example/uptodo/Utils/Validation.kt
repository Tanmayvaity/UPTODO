package com.example.uptodo.Utils

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.view.View
import com.google.android.material.textfield.TextInputLayout
import com.google.android.play.integrity.internal.t
import java.util.regex.Pattern

class Validation(
) {

    private val userRegex = "^(?![_\\.-]+\$)[a-zA-Z0-9]+(?:[_\\.-][a-zA-Z0-9]+)*(?<!\\.com)\$"
    private val passwordRegex = "^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-z])" +         //at least 1 lower case letter
            "(?=.*[A-Z])" +         //at least 1 upper case letter
//            "(?=.*[a-zA-Z])" +      //any letter
            "(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{8,}" +               //at least 4 characters
            "$"

    fun validateUsername(
        username: String,
        tl: TextInputLayout
    ) {
        if (isEmptyOrBlank(username)) {
            tl.error = "Username cannot be Empty or blank"
            return
        }

        if (!isValidLengthUsername(username)) {
            tl.error = "Username characters should be between 8 and 25 letters long"
            return
        }

        if (!(isValidText(username, userRegex))) {
            tl.error = "Username can only have characters from [A-Z],[a-z] numbers" +
                    " from [0-9] and [-_,] special characters and cannot end with .com"
            return
        }

        tl.error = null
    }

    fun validateEmail(
        email: String,
        tl: TextInputLayout
    ) {
        if (isEmptyOrBlank(email)) {
            tl.error = "Email cannot be Empty or blank"
            return
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tl.error = "Not a valid Email"
            return
        }

        tl.error = null
    }

    fun validatePassword(
        password: String,
        tl: TextInputLayout
    ) {
        if (isEmptyOrBlank(password)) {
            tl.error = "password cannot be Empty or blank"
            return
        }
        val value = isPasswordValid(password)
        Log.d("password", value.toString())

        if (!isPasswordValid(password)) {
            tl.error =
                "password should atleast contain 4 characters,1 Uppercase,1 Lowercase and 1  Symbol"

            return
        }

        tl.error = null


    }
    // password is the text of current edittext
    // confirm password is the text of original set password
    fun validateConfirmPassword(password:String,confirmPassword:String,tl: TextInputLayout){

        if(isEmptyOrBlank(password)){
            tl.error = "field cannot be empty"
            return
        }


        if(!isPasswordSame(password,confirmPassword)){
            tl.error = "password not same"
            return
        }
        tl.error = null
    }

    private fun isPasswordSame(password:String,confirmPassword:String):Boolean{
        return password.equals(confirmPassword)
    }

    private fun isPasswordValid(password: String): Boolean {
        val pattern = Pattern.compile(passwordRegex)
        val matcher = pattern.matcher(password)

        return matcher.matches()
    }

    private fun isValidLengthEmail(text: String): Boolean {

        if (text.length < 8 || text.length > 50) {
            return false
        }

        return true
    }


    private fun isValidText(username: String, userRegex: String): Boolean {

        if (Regex(userRegex).matches(username)) return true

        return false
    }

    private fun isEmptyOrBlank(text: String): Boolean {

        if (text.isEmpty() || text.isBlank()) {
            return true
        }

        return false
    }

    private fun isValidLengthUsername(text: String): Boolean {

        if (text.length < 8 || text.length > 25) {
            return false
        }

        return true
    }
}