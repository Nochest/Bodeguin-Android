package com.example.bodeguinandroid.service.auth


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/usuarios/log")
    fun authenticate(@Body authRequest: AuthRequest): Call<AuthResponse>
}
class AuthResponse(
    var id: Int,
    var correo: String,
    var password: String,
    var nombre:String,
    var apellidoPaterno:String,
    var apellidoMaterno:String,
    var direccion: String,
    var dni: String,
    var enable: Boolean = true,
    var adm: Boolean = false
)

class AuthRequest(
    var correo : String,
    var password : String
)

