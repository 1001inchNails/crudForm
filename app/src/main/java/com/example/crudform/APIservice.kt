package com.example.crudform

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("create")
    fun crearFicha(@Body data: FichaCreate): Call<Response>

    @POST("read")
    fun crearFicha(@Body data: FichaRead): Call<Response>

    @POST("update")
    fun crearFicha(@Body data: FichaUpdate): Call<Response>

    @POST("delete")
    fun crearFicha(@Body data: FichaDelete): Call<Response>

}