package com.example.crudform

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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
                Toast.makeText(this, "Rellena todos los campos menos Indice", Toast.LENGTH_SHORT).show()
            } else {
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
            }else{
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
            if (indice.isBlank()){
                Toast.makeText(this, "Introduzca Indice para modificar", Toast.LENGTH_SHORT).show()
            }else {
                // todo: enviar a backend y cambiar en la entrada con el indice correspondiente
                val indice = textNombre.text.toString()
                val nombre = textNombre.text.toString()
                val clase = textClase.text.toString()
                // todo: verificar operacion desde back
                Toast.makeText(this, "Ficha modificada", Toast.LENGTH_SHORT).show()

            }
        }

        btnBorrar.setOnClickListener {
            val indice = textNombre.text.toString()
            if (indice.isBlank()){
                Toast.makeText(this, "Introduzca Indice para borrar", Toast.LENGTH_SHORT).show()
            }else {
                // todo: enviar a backend y borrar entrada con el indice correspondiente
                // todo: verificar operacion desde back
                Toast.makeText(this, "Ficha borrada", Toast.LENGTH_SHORT).show()

            }
        }
    }
}