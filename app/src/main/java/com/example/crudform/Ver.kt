package com.example.crudform

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
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

class Ver : AppCompatActivity() {

    private var listaFichas: ArrayList<FichaAll> = ArrayList()
    private lateinit var spinnerAdapter: ArrayAdapter<String>

    private lateinit var textIndice: EditText
    private lateinit var textNombre: EditText
    private lateinit var textClase: EditText
    private lateinit var textHP: EditText

    private lateinit var opciones: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver)

        opciones = findViewById(R.id.dropDownMenu)
        textNombre = findViewById<EditText>(R.id.nombre)
        textIndice = findViewById<EditText>(R.id.indice)
        textClase = findViewById<EditText>(R.id.clase)
        textHP = findViewById<EditText>(R.id.HP)

        textIndice.setFocusable(false);
        textIndice.setFocusableInTouchMode(false);
        textIndice.setCursorVisible(false);

        textNombre.setFocusable(false);
        textNombre.setFocusableInTouchMode(false);
        textNombre.setCursorVisible(false);

        textClase.setFocusable(false);
        textClase.setFocusableInTouchMode(false);
        textClase.setCursorVisible(false);

        textHP.setFocusable(false);
        textHP.setFocusableInTouchMode(false);
        textHP.setCursorVisible(false);

        spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, ArrayList<String>())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        opciones.adapter = spinnerAdapter

        spinnerAdapter.add("Cargando opciones...")


        verTodos()

        val btnVer = findViewById<Button>(R.id.buttVer)
        val btnVolver = findViewById<Button>(R.id.buttVolver)

        btnVer.setOnClickListener {
            val selectedText = opciones.selectedItem.toString()

            val partes = selectedText.split(" - ")
            if (partes.size >= 2) {
                val indiceSeleccionado = partes[0].toIntOrNull()

                if (indiceSeleccionado != null) {
                    val fichaSeleccionada = listaFichas.find { it.indice == indiceSeleccionado }

                    if (fichaSeleccionada != null) {
                        textIndice.setText(fichaSeleccionada.indice.toString())
                        textNombre.setText(fichaSeleccionada.nombre)
                        textClase.setText(fichaSeleccionada.clase)
                        textHP.setText(fichaSeleccionada.HP.toString())

                    } else {
                        Toast.makeText(this@Ver, "No se encontró el elemento", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(this@Ver, "Error al convertir el índice", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this@Ver, "Selección inválida", Toast.LENGTH_SHORT).show()
            }
        }

        btnVolver.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun verTodos() {
        RetrofitClient.instance.verTodas().enqueue(object : Callback<RespuestaAll> {
            override fun onResponse(call: Call<RespuestaAll>, response: Response<RespuestaAll>) {
                if (response.isSuccessful) {
                    val respuesta = response.body()

                    if (respuesta != null) {
                        when (respuesta.type) {
                            "success" -> {
                                val data: List<FichaAll>? = response.body()?.data as List<FichaAll>?

                                listaFichas.clear()
                                spinnerAdapter.clear()


                                data?.forEach { modulo ->
                                    listaFichas.add(modulo)
                                    val displayText = "${modulo.indice} - ${modulo.nombre}"
                                    spinnerAdapter.add(displayText)
                                    //System.out.println(listaFichas)
                                }


                            }

                            "failure" -> {
                                spinnerAdapter.clear()
                                spinnerAdapter.add("No hay datos disponibles")
                                Toast.makeText(this@Ver, respuesta.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                } else {
                    spinnerAdapter.clear()
                    Toast.makeText(this@Ver, "Error al obtener los datos", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<RespuestaAll>, t: Throwable) {
                spinnerAdapter.clear()
                spinnerAdapter.add("Error de conexión")
                Toast.makeText(this@Ver, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
                System.out.println(t.message)
            }
        })
    }


    private fun limpiarCampos() {
        findViewById<EditText>(R.id.indice).text.clear()
        findViewById<EditText>(R.id.nombre).text.clear()
        findViewById<EditText>(R.id.clase).text.clear()
        findViewById<EditText>(R.id.HP).text.clear()
    }

}