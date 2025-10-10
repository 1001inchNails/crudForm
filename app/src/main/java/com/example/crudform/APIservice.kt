package com.example.crudform

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("create")
    fun crearFicha(@Body data: FichaCreate): Call<Respuesta>

    @POST("read")
    fun leerFicha(@Body data: FichaRead): Call<Respuesta>

    @POST("update")
    fun actualizarFicha(@Body data: FichaUpdate): Call<Respuesta>

    @POST("delete")
    fun borrarFicha(@Body data: FichaDelete): Call<Respuesta>
}