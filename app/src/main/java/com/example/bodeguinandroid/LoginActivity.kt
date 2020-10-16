package com.example.bodeguinandroid

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.bodeguinandroid.network.ApiGateway
import com.example.bodeguinandroid.service.auth.AuthRequest
import com.example.bodeguinandroid.service.auth.AuthResponse
import com.example.bodeguinandroid.service.auth.AuthService
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson
import org.json.JSONStringer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var loginButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = getSharedPreferences("db_local",0)
        super.onCreate(savedInstanceState)
        if(sharedPreferences.contains("id")){
            val intent = Intent(applicationContext, MenuActivity::class.java)
            startActivity(intent)
        }
        else{
            setContentView(R.layout.activity_login)
            val registerButton = findViewById<MaterialTextView>(R.id.btnHere)
            loginButton = findViewById<MaterialButton>(R.id.logButton)
            loginButton.setOnClickListener { auth() }
            registerButton.setOnClickListener { navigateToRegister() }
        }
    }

    private fun auth(){
        val service = ApiGateway().api.create(AuthService::class.java)
        val email = findViewById<TextInputEditText>(R.id.itEmail).text
        val password = findViewById<TextInputEditText>(R.id.itPassword).text
        val authRequest = AuthRequest(email.toString(), password.toString())
        loginButton.visibility =View.GONE
        service.authenticate(authRequest).enqueue(object : Callback<AuthResponse> {
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                println(t)
            }

            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val res = response.body()
                    val jsonString = Gson().toJson(res)
                    val v_res: AuthResponse = Gson().fromJson(jsonString, AuthResponse::class.java)

                    val intent = Intent(applicationContext, MenuActivity::class.java)
                    startActivity(intent)
                    saveData(v_res.id,v_res.correo, v_res.password, v_res.nombre, v_res.apellidoPaterno, v_res.apellidoMaterno, v_res.direccion, v_res.dni, v_res.enable, v_res.adm)
                    finish()
                } else {
                    when (response.code()) {
                        404 -> {
                            Toast.makeText(applicationContext, "Credenciales invalidas", Toast.LENGTH_SHORT).show()
                            loginButton.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })
    }
    private fun navigateToRegister(){
        val intent = Intent(applicationContext,RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun saveData(id: Int, correo: String, password: String,  nombre: String, apellidoPaterno :String,
                         apellidoMaterno: String,  direccion: String,dni: String,enable: Boolean,adm: Boolean){

        val editor:SharedPreferences.Editor = getSharedPreferences("db_local",0).edit()
        editor.putInt("id", id)
        editor.putString("correo", correo)
        editor.putString("password", password)
        editor.putString("nombre", nombre)
        editor.putString("apellidoPaterno", apellidoPaterno)
        editor.putString("apellidoMaterno", apellidoMaterno)
        editor.putString("direccion", direccion)
        editor.putString("dni", dni)
        editor.putBoolean("enable", enable)
        editor.putBoolean("adm", adm)
        editor.apply()
    }
}