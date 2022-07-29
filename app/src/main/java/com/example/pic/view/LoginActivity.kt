package com.example.pic.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pic.R
import com.example.pic.databinding.ActivityLoginBinding
import com.example.pic.model.User
import com.example.pic.viewModel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Matcher
import java.util.regex.Pattern


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var email: String
    private lateinit var pass: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)


        checkIfUserLogged()

        binding.btnLogin.setOnClickListener {
            email = binding.emailLoginTil.editText?.text.toString()
            pass = binding.passwordLoginTil.editText?.text.toString()

            if (email.isEmpty() || pass.isEmpty()){
                showSnackBar("Fill blank","Email or Password cant be empty")
            }else if (!isValidEmail(email)){
                showSnackBar("Invalid Email", "Please enter a valid email")
            }else if (pass.length < 8){
                showSnackBar("Invalid Password", "your password must be more than 8 characters")
            }else if(viewModel.isUserExist(email, pass) == Pair(true, second = false)){
                showSnackBar("Password is incorrect", "check your password, and try again!")
            }else if (viewModel.isUserExist(email, pass) == Pair(true, second = true)){
                startActivity(Intent(this, MainActivity::class.java))
                submitLogin(email)
                finish()
            }
            else {
                viewModel.addUser(
                    User(
                        0,
                        email,
                        pass,
                        "-",
                        "-",
                        null
                    )
                )
            }
        }
    }

    private fun checkIfUserLogged() {
        val sharedPreference =  getSharedPreferences("UserData", Context.MODE_PRIVATE)
        if (sharedPreference.getString("email",null) != null)
            startActivity(Intent(this, MainActivity::class.java))

    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target!!).matches()
        }
    }

    @SuppressLint("InflateParams", "CutPasteId")
    private fun showSnackBar(errorTitle: String, errorMassage: String){

        val snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_LONG)

        val layout = snackbar.view as SnackbarLayout
        val snackView: View = LayoutInflater.from(applicationContext).inflate(R.layout.my_snackbar, null)
        val errorTitleTV: TextView = snackView.findViewById(R.id.txt_error_title)
        val errorMassageTV: TextView = snackView.findViewById(R.id.txt_error_massage)

        errorTitleTV.text = errorTitle
        errorMassageTV.text = errorMassage

        layout.addView(snackView, 0)

        snackbar.show()
    }

    private fun isValidPassword(password: String): Boolean {
        val pattern: Pattern

        val passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$"
        pattern = Pattern.compile(passwordPattern)
        val matcher: Matcher = pattern.matcher(password)

        return matcher.matches()
    }

    private fun submitLogin(email: String){

        val sharedPreference =  getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("email",email)
        editor.apply()
    }
}