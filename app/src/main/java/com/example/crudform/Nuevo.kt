package com.example.crudform

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
            try {
                val hp = HP.toInt()


                if (nombre.isBlank() || clase.isBlank() || HP.isBlank() || hp <= 0) {
                    Toast.makeText(this, "Rellena todos los campos correctamente", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    mostrarDialogoConfirmacion( nombre, clase, HP )
                }


            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Rellena todos los campos (HP debe ser un numero entero positivo)",
                    Toast.LENGTH_SHORT).show()
            }




        }

        btnVolver.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun mostrarDialogoConfirmacion(nombre: String, clase: String, hp: String) {
        val builder = AlertDialog.Builder(this, R.style.custom_dialog)
        builder.setTitle("Confirmar nueva ficha")
        builder.setMessage("¿Estás seguro de crear esta ficha?")

        builder.setPositiveButton("Crear") { dialog, which ->
            val fichaCreate = FichaCreate(
                nombre = nombre,
                clase = clase,
                HP = hp
            )
            crearFicha(fichaCreate)
        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE)

            positiveButton.setTextColor(resources.getColor(R.color.warning))
        }
        dialog.show()
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
                Toast.makeText(this@Nuevo, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
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