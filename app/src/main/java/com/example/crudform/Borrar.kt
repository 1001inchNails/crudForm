package com.example.crudform

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Borrar : AppCompatActivity() {

    private var listaFichas: ArrayList<FichaAll> = ArrayList()
    private lateinit var spinnerAdapter: ArrayAdapter<String>

    private lateinit var textIndice: EditText
    private lateinit var textNombre: EditText
    private lateinit var textClase: EditText
    private lateinit var textHP: EditText

    private lateinit var opciones: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_borrar)

        opciones = findViewById(R.id.dropDownMenu)
        textNombre = findViewById(R.id.nombre)
        textIndice = findViewById(R.id.indice)
        textIndice.setFocusable(false);
        textIndice.setFocusableInTouchMode(false);
        textIndice.setCursorVisible(false);
        textClase = findViewById(R.id.clase)
        textHP = findViewById(R.id.HP)

        spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ArrayList())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        opciones.adapter = spinnerAdapter

        spinnerAdapter.add("Cargando opciones...")

        verTodos()

        opciones.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {
                if (position >= 0 && position < listaFichas.size) {
                    val fichaSeleccionada = listaFichas[position]
                    cargarDatosEnCampos(fichaSeleccionada)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // opcional
            }
        }

        val btnModificar = findViewById<Button>(R.id.buttBorrar)
        val btnVolver = findViewById<Button>(R.id.buttVolver)

        btnModificar.setOnClickListener {
            val indice = textIndice.text.toString()
            if (indice.isBlank()) {
                Toast.makeText(this, "Elija opcion", Toast.LENGTH_SHORT).show()
            } else {
                val indice = textIndice.text.toString()
                if (indice.isBlank()) {
                    Toast.makeText(this, "Introduzca Indice para borrar", Toast.LENGTH_SHORT).show()
                } else {
                    val fichaDelete = FichaDelete(
                        indice = indice
                    )
                    borrarFicha(fichaDelete)

                }

            }
        }

        btnVolver.setOnClickListener {
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
                                val data = respuesta.data as? List<FichaAll>

                                listaFichas.clear()
                                spinnerAdapter.clear()

                                if (!data.isNullOrEmpty()) {
                                    listaFichas.addAll(data)

                                    data.forEach { ficha ->
                                        val displayText = "${ficha.indice} - ${ficha.nombre}"
                                        spinnerAdapter.add(displayText)
                                    }

                                    opciones.setSelection(0)
                                    cargarDatosEnCampos(listaFichas[0])

                                    Toast.makeText(
                                        this@Borrar,
                                        "Datos cargados correctamente",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    spinnerAdapter.add("No hay datos disponibles")
                                    Toast.makeText(
                                        this@Borrar,
                                        "No hay datos disponibles",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            "failure" -> {
                                spinnerAdapter.clear()
                                spinnerAdapter.add("No hay datos disponibles")
                                Toast.makeText(this@Borrar, respuesta.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                } else {
                    spinnerAdapter.clear()
                    spinnerAdapter.add("Error al obtener los datos")
                    Toast.makeText(this@Borrar, "Error al obtener los datos", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<RespuestaAll>, t: Throwable) {
                spinnerAdapter.clear()
                spinnerAdapter.add("Error de conexión")
                Toast.makeText(this@Borrar, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun cargarDatosEnCampos(ficha: FichaAll) {
        textIndice.setText(ficha.indice.toString())
        textNombre.setText(ficha.nombre)
        textClase.setText(ficha.clase)
        textHP.setText(ficha.HP.toString())

    }

    private fun borrarFicha(fichaDelete: FichaDelete) {
        RetrofitClient.instance.borrarFicha(fichaDelete).enqueue(object : Callback<Respuesta> {
            override fun onResponse(call: Call<Respuesta>, response: Response<Respuesta>) {
                if (response.isSuccessful) {
                    val respuesta = response.body()
                    //System.out.println(respuesta)
                    if (respuesta?.type == "failure") {
                        limpiarCampos()
                        Toast.makeText(this@Borrar, "Ficha no encontrada", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        limpiarCampos()
                        Toast.makeText(this@Borrar, "Ficha borrada", Toast.LENGTH_LONG).show()
                        verTodos()
                    }

                } else {
                    limpiarCampos()
                    Toast.makeText(this@Borrar, "Ficha no encontrada", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Respuesta>, t: Throwable) {
                Toast.makeText(this@Borrar, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
                System.out.println(t.message);
            }
        })
    }

    private fun limpiarCampos() {
        textIndice.text.clear()
        textNombre.text.clear()
        textClase.text.clear()
        textHP.text.clear()
    }
}
