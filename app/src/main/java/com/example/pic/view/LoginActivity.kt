package com.example.pic.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import com.example.pic.model.entity.UserEntity
import com.example.pic.util.showSnackBar
import com.example.pic.view.custom.CustomButton
import com.example.pic.viewModel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
            binding.emailLoginTil.isErrorEnabled = false
            binding.passwordLoginTil.isErrorEnabled = false

            viewModel.isUserExist(email).observe(this) { userExist ->
                if (userExist) {

                    viewModel.getUser(email).observe(this) { user ->
                        if (user?.password != pass) {
                            setErrorEditText("Something wrong!", "Check your email or password and try again!", "password")
                        } else {
                            submitLogin(email)
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }

                } else {
                    if (email.isEmpty()) {
                        setErrorEditText("Fill blank", "Email cant be empty","email")
                    }else if (pass.isEmpty()){
                        setErrorEditText("Fill blank", "Password cant be empty","password")
                    }
                    else if (!isValidEmail(email)) {
                        setErrorEditText("Invalid Email", "Please enter a valid email","email")
                    } else if (pass.length < 8) {
                        setErrorEditText(
                            "Invalid Password",
                            "your password must be more than 8 characters",
                            "password"
                        )
                    } else if (!isValidPassword(pass)) {
                        setErrorEditText("Easy Password", "use at least one word","password")
                    } else {
                        binding.btnLogin.enableProgress = true
                        viewModel.addUser(
                            UserEntity(
                                0,
                                email,
                                pass,
                                "-",
                                "-",
                                null
                            )
                        )

                        submitLogin(email)
                        binding.btnLogin.type = CustomButton.Types.Success

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()

                    }
                }
            }

        }
    }

    private fun checkIfUserLogged() {
        val sharedPreference = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        if (sharedPreference.getString("email", null) != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target!!).matches()
        }
    }

    @SuppressLint("InflateParams", "CutPasteId")
    private fun setErrorEditText(errorTitle: String, errorMassage: String, status: String) {

        if (status == "email"){
            binding.emailLoginTil.error = "error"
            if (binding.emailLoginTil.childCount == 2)
                binding.emailLoginTil.getChildAt(1).visibility = View.GONE
        }else{
            binding.passwordLoginTil.error = "error"
            if (binding.passwordLoginTil.childCount == 2)
                binding.passwordLoginTil.getChildAt(1).visibility = View.GONE
        }

        showSnackBar(errorTitle,errorMassage,binding.root,applicationContext)


    }


    private fun isValidPassword(password: String): Boolean {
        val pattern: Pattern

        val passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$"
        pattern = Pattern.compile(passwordPattern)
        val matcher: Matcher = pattern.matcher(password)

        return matcher.matches()
    }

    private fun submitLogin(email: String) {

        val sharedPreference = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("email", email)
        editor.apply()
    }


}