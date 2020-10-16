package com.example.bodeguinandroid

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MenuActivity : AppCompatActivity() {
    lateinit var logout: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        logout = findViewById(R.id.imageLogout)

        logout.setOnClickListener{ logout() }

    }

    private fun logout(){
        val editor:SharedPreferences.Editor = getSharedPreferences("db_local", 0).edit()
        editor.remove("id")
        editor.remove("correo")
        editor.remove("password")
        editor.remove("nombre")
        editor.remove("apellidoPaterno")
        editor.remove("apellidoMaterno")
        editor.remove("direccion")
        editor.remove("dni")
        editor.remove("enable")
        editor.remove("adm")
        editor.apply()
        finish()
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }
}