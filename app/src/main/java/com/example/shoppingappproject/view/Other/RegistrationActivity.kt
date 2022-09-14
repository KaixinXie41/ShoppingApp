package com.example.shoppingappproject.view.Other

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.shoppingappproject.databinding.ActivityRegistrationBinding
import com.example.shoppingappproject.model.remote.VolleyHandler
import com.example.shoppingappproject.model.remote.data.user.User
import com.example.shoppingappproject.presenter.registration.RegistrationMVP
import com.example.shoppingappproject.presenter.registration.RegistrationPresenter
import java.util.regex.Pattern

class RegistrationActivity : AppCompatActivity(), RegistrationMVP.RegistrationView {
    private lateinit var binding:ActivityRegistrationBinding
    private lateinit var presenter: RegistrationPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpEvent()
    }

    private fun isPhoneValid(phone:String):Boolean{
        val patterns =  "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$"
        return Pattern.compile(patterns).matcher(phone).matches()
    }
    private fun isEmailValid(str:String):Boolean{
        val email_address_pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
        return email_address_pattern.matcher(str).matches()
    }
    private fun setUpEvent() {
        val btnRegister: Button = binding.btnRegister
        val edtEmail: EditText = binding.edtEmail
        val edtName: EditText = binding.edtName
        val edtPhone: EditText = binding.edtMobile
        val edtPassword: EditText = binding.edtPassword
        val txtGoToLogin: TextView = binding.txtGoToLogin

        presenter = RegistrationPresenter(VolleyHandler(this), this)

        btnRegister.setOnClickListener {
            val name = edtName.text.toString()
            val phone = edtPhone.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val user = User(null, name, phone, email, password)
            presenter.registerUser(user)
            isEmailValid(email)
            isPhoneValid(phone)
        }
            txtGoToLogin.setOnClickListener{
                val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                startActivity(intent)
            }

        }

    override fun setResult(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onLoad(isLoading: Boolean) {
        if(isLoading){
            binding.circularProgress.visibility = View.VISIBLE
        }else{
            binding.circularProgress.visibility = View.GONE
        }
    }

    override fun setLogin(user: User) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}