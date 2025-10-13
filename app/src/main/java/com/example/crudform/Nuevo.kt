package com.example.crudform

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.crudform.databinding.ActivityNuevoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Nuevo : AppCompatActivity() {

    private lateinit var textNombre: EditText
    private lateinit var textClase: EditText
    private lateinit var textHP: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo)

        textNombre = findViewById<EditText>(R.id.nombre)
        textClase = findViewById<EditText>(R.id.clase)
        textHP = findViewById<EditText>(R.id.HP)

        val btnNuevo = findViewById<Button>(R.id.buttNuevo)
        val btnVolver = findViewById<Button>(R.id.buttVolver)

        btnNuevo.setOnClickListener {

            val nombre = textNombre.text.toString()
            val clase = textClase.text.toString()
            val HP = textHP.text.toString()

            if (nombre.isBlank() || clase.isBlank() || HP.isBlank()) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val fichaCreate = FichaCreate(
                    nombre = nombre,
                    clase = clase,
                    HP = HP
                )
                crearFicha(fichaCreate)
            }
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun crearFicha(fichaCreate: FichaCreate) {
        RetrofitClient.instance.crearFicha(fichaCreate).enqueue(object : Callback<Respuesta> {
            override fun onResponse(call: Call<Respuesta>, response: Response<Respuesta>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@Nuevo, "Nueva ficha creada", Toast.LENGTH_SHORT).show()
                    limpiarCampos()
                } else {
                    limpiarCampos()
                    Toast.makeText(this@Nuevo, "Error al crear ficha", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Respuesta>, t: Throwable) {
                Toast.makeText(this@Nuevo, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                System.out.println(t.message);
            }
        })
    }

    private fun limpiarCampos() {
        findViewById<EditText>(R.id.nombre).text.clear()
        findViewById<EditText>(R.id.clase).text.clear()
        findViewById<EditText>(R.id.HP).text.clear()
    }

}