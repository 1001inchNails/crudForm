package com.example.crudform

import com.google.gson.JsonObject

data class FichaCreate(
    val nombre: String,
    val clase: String,
    val HP: String
)

data class FichaRead(
    val filtroKey: String,
    val filtroValue: String
)

data class FichaUpdate(
    val indice: String,
    val nombre: String,
    val clase: String,
    val HP: String
)

data class FichaDelete(
    val indice: String
)

data class Respuesta(
    val type: String,
    val message: String,
    val data: JsonObject? = null,
    )

data class RespuestaAll(
    val type: String,
    val message: String,
    val data: List<FichaAll>
)

data class FichaAll(
    val _id: String,
    val nombre: String,
    val clase: String,
    val HP: Int,
    val indice: Int,
    val __v: Int? = null
)
