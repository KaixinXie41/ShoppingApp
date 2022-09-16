package com.example.shoppingappproject.view.other

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.shoppingappproject.databinding.ActivityLoginBinding
import com.example.shoppingappproject.model.remote.VolleyHandler
import com.example.shoppingappproject.model.remote.data.user.User
import com.example.shoppingappproject.presenter.login.LoginMVP
import com.example.shoppingappproject.presenter.login.LoginPresenter

class LoginActivity : AppCompatActivity() , LoginMVP.LoginView{
    private lateinit var binding:ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var presenter: LoginMVP.LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpEvent()
    }

    private fun setUpEvent() {
        val buttonLogin: Button = binding.btnLogin
        val loginEmail: EditText = binding.edtEmail
        val loginPassword: EditText = binding.edtPassword
        val txtCreateAccount: TextView = binding.createAccount

        presenter = LoginPresenter(VolleyHandler(this), this)
        buttonLogin.setOnClickListener {
            val email = loginEmail.text.toString()
            val password = loginPassword.text.toString()
            val user = User(null, null, null, email, password)
            presenter.userLogin(user)
        }

        txtCreateAccount.setOnClickListener {
            val registerIntent:Intent = Intent(this, RegistrationActivity::class.java)
            startActivity(registerIntent)
        }
    }

    override fun setResult(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onLoad(isLoading: Boolean) {
    }

    override fun setLogin(user:User){
        val loginIntent:Intent = Intent(this, MainActivity::class.java)
        sharedPreferences = getSharedPreferences(Account_Information, MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.apply{
            putString(USER_ID, user.userID)
            putString(USER_NAME, user.fullName)
            putString(USER_MOBILE, user.mobileNo)
            putString(USER_EMAIL, user.emailId)
            putString(USER_PASSWORD, user.password)
            apply()
            startActivity(loginIntent)
        }
    }

    companion object{
        const val Account_Information = "Account information"
        const val USER_ID = "userID"
        const val USER_NAME ="name"
        const val USER_MOBILE = "mobile"
        const val USER_EMAIL ="email"
        const val USER_PASSWORD = "password"
    }
}