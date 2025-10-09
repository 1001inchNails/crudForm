package com.example.crudform

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textIndice = findViewById<EditText>(R.id.indice)
        val textNombre = findViewById<EditText>(R.id.nombre)
        val textClase = findViewById<EditText>(R.id.clase)
        val textHP = findViewById<EditText>(R.id.HP)

        val btnNuevo = findViewById<Button>(R.id.buttNuevo)
        val btnVer = findViewById<Button>(R.id.buttVer)
        val btnCambiar = findViewById<Button>(R.id.buttCambiar)
        val btnBorrar = findViewById<Button>(R.id.buttBorrar)

        btnNuevo.setOnClickListener {
            val nombre = textNombre.text.toString()
            val clase = textClase.text.toString()
            val HP = textHP.text.toString()

            if (nombre.isBlank() || clase.isBlank() || HP.isBlank()) {
                Toast.makeText(this, "Rellena todos los campos menos Indice", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val fichaCreate = FichaCreate(
                    nombre = nombre,
                    clase = clase,
                    HP = HP
                )

                crearFicha(fichaCreate)
                // todo: mandar a back, seguir si hay respuesta correcta
                Toast.makeText(
                    this,
                    "Nueva ficha creada",
                    Toast.LENGTH_LONG
                ).show()
            }
        }



        btnVer.setOnClickListener {
            val indice = textNombre.text.toString()
            val nombre = textNombre.text.toString()
            val clase = textClase.text.toString()
            val HP = textHP.text.toString()
            if (indice.isBlank() && nombre.isBlank() && clase.isBlank() && HP.isBlank()) {
                Toast.makeText(this, "Rellene un campo", Toast.LENGTH_SHORT).show()
            } else {
                val filtroKey: String;
                val filtroValue: String;
                if (indice.isNotBlank()) {
                    filtroKey = "indice";
                    filtroValue = indice;
                } else if (nombre.isNotBlank()) {
                    filtroKey = "nombre";
                    filtroValue = nombre;
                } else if (clase.isNotBlank()) {
                    filtroKey = "clase";
                    filtroValue = clase;
                } else if (HP.isNotBlank()) {
                    filtroKey = "HP";
                    filtroValue = HP;
                }

                // todo: enviar filtroKey y filtroValue en body

                // todo: hacer la llamada a backend, con todos los valores, manejarlos en el back (separar "blank" del que lleve datos)
                // todo: cojer los valores del back que encuentre con lo que haya escrito el user en alguno de los inputs
                val indiceEncontrado: Int;
                val nombreEncontrado: String;
                val ClaseEncontrada: String;
                val hpEncontrado: Int;

                Toast.makeText(this, "Desplegando datos", Toast.LENGTH_SHORT).show()
            }

        }

        btnCambiar.setOnClickListener {
            val indice = textNombre.text.toString()
            if (indice.isBlank()) {
                Toast.makeText(this, "Introduzca Indice para modificar", Toast.LENGTH_SHORT).show()
            } else {
                // todo: enviar a backend y cambiar en la entrada con el indice correspondiente
                val indice = textIndice.text.toString()
                val nombre = textNombre.text.toString()
                val clase = textClase.text.toString()
                val hp = textHP.text.toString()
                // todo: verificar operacion desde back
                Toast.makeText(this, "Ficha modificada", Toast.LENGTH_SHORT).show()

            }
        }

        btnBorrar.setOnClickListener {
            val indice = textNombre.text.toString()
            if (indice.isBlank()) {
                Toast.makeText(this, "Introduzca Indice para borrar", Toast.LENGTH_SHORT).show()
            } else {
                // todo: enviar a backend y borrar entrada con el indice correspondiente
                // todo: verificar operacion desde back
                Toast.makeText(this, "Ficha borrada", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun crearFicha(fichaCreate: FichaCreate) {
        RetrofitClient.instance.crearFicha(fichaCreate).enqueue(object : Callback<FichaCreate> {
            override fun onResponse(call: Call<Response>, response: Response<Response>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Nueva ficha creada", Toast.LENGTH_LONG).show()
                    limpiarCampos()
                } else {
                    Toast.makeText(this@MainActivity, "Error al crear ficha", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Personaje>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}