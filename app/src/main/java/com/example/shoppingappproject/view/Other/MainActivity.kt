package com.example.shoppingappproject.view.Other

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.shoppingappproject.R
import com.example.shoppingappproject.databinding.ActivityMainBinding
import com.example.shoppingappproject.model.remote.VolleyHandler
import com.example.shoppingappproject.presenter.logout.LogoutMVP
import com.example.shoppingappproject.presenter.logout.LogoutPresenter
import com.example.shoppingappproject.view.supportChat.SupportChatActivity

import com.google.android.material.navigation.NavigationView
import com.google.android.material.progressindicator.CircularProgressIndicator

class MainActivity : AppCompatActivity(), LogoutMVP.LogoutView {

    private lateinit var binding:ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var drawerLayout: DrawerLayout
    private lateinit var presenter:LogoutPresenter
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar =findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        drawerLayout = findViewById(R.id.drawer_layout)

        sharedPreferences = getSharedPreferences(LoginActivity.Account_Information, MODE_PRIVATE)
        editor = sharedPreferences.edit()

        presenter = LogoutPresenter(VolleyHandler(this),this)
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    val email = sharedPreferences.getString(LoginActivity.USER_EMAIL, "-1")
                    email?.let {
                        presenter.userLogout(email)
                    }
                }
                else -> {
                    menuItem.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))
                            ||super.onOptionsItemSelected(menuItem)
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
            true
        }
        val navView = navigationView.inflateHeaderView(R.layout.nav_header)
        val nHName :TextView = navView.findViewById(R.id.txt_nv_head_name)
        val nHEmail : TextView = navView.findViewById(R.id.txt_nv_head_email)
        val nHPhone : TextView = navView.findViewById(R.id.txt_nv_head_phone)

        nHName.text = sharedPreferences.getString(LoginActivity.USER_NAME,LoginActivity.USER_NAME)
        nHEmail.text = sharedPreferences.getString(LoginActivity.USER_EMAIL,LoginActivity.USER_EMAIL)
        nHPhone.text = sharedPreferences.getString(LoginActivity.USER_MOBILE,LoginActivity.USER_MOBILE)

        val navHost : NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as
                NavHostFragment
        val navController = navHost.navController
        val drawerLayout : DrawerLayout?= findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home_page, R.id.nav_cart_page, R.id.nav_order_page),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent:Intent = Intent(this@MainActivity, SupportChatActivity::class.java)
        when(item.itemId){
            R.id.btnChat ->
                startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun setResult(message: String) {
        val intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    override fun onLoad(isLoading: Boolean) {
        if(isLoading){
            findViewById<CircularProgressIndicator>(R.id.circularProgressBar)?.visibility = View.VISIBLE
        } else {
            findViewById<CircularProgressIndicator>(R.id.circularProgressBar)?.visibility = View.GONE
        }
    }

}